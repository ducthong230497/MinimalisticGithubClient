package com.github.minimalisticclient.domain.use_cases

import com.github.minimalisticclient.domain.entities.DomainResult
import com.github.minimalisticclient.domain.entities.filterSuccessOrError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

abstract class SingleSourceStrategyUseCase<T> {

    fun <T, A> executeStrategy(
        databaseQuery: suspend () -> Flow<DomainResult<T?>>,
        networkCall: suspend () -> DomainResult<A>,
        saveCallResult: suspend (A?) -> Unit
    ): Flow<DomainResult<T>> {
        return combine(
            flow { emitAll(databaseQuery.invoke().filterSuccessOrError()) },
            flow {
                emit(DomainResult.loading())
                val network = networkCall.invoke()
                when (network.status) {
                    DomainResult.Status.ERROR -> emit(
                        DomainResult.error(
                            network.error ?: Exception("Error when load network request SingleSourceStrategyUseCase"), null
                        )
                    )
                    DomainResult.Status.SUCCESS -> {
                        // check if network data is empty => emit success
                        val data = network.data
                        val hasData: Boolean = if (data is Collection<*>) {
                            !data.isNullOrEmpty()
                        } else {
                            data != null
                        }
                        if (!hasData) {
                            emit(DomainResult.success(null))
                        }

                        withContext(Dispatchers.IO) { saveCallResult(network.data) }
                    }
                    else -> {
                    }
                }
            }.catch { emit(DomainResult.error(it)) }) { cache, network ->

            val cacheData = cache.data
            val hasCache: Boolean = if (cacheData is Collection<*>) {
                !cacheData.isNullOrEmpty()
            } else {
                cacheData != null
            }

            return@combine when {
                hasCache -> DomainResult.success(cache.data)
                else -> {
                    if (network.status == DomainResult.Status.ERROR || cache.status == DomainResult.Status.ERROR) {
                        DomainResult.error(network.error ?: cache.error ?: Exception("Error when download data"), null)
                    } else if (network.status == DomainResult.Status.SUCCESS) {
                        DomainResult.success(cache.data)
                    } else {
                        DomainResult.loading()
                    }
                }
            }
        }
    }
}
