package com.github.minimalisticclient.domain.entities

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull

data class DomainResult<out T>(val status: Status, val data: T?, val error: Throwable?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): DomainResult<T> {
            return DomainResult(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable, data: T? = null): DomainResult<T> {
            return DomainResult(Status.ERROR, data, error)
        }

        fun <T> loading(data: T? = null): DomainResult<T> {
            return DomainResult(Status.LOADING, data, null)
        }
    }
}

fun <T> Flow<DomainResult<T>>.filterSuccessOrError(): Flow<DomainResult<T>> {
    return filter { it.status == DomainResult.Status.ERROR || it.status == DomainResult.Status.SUCCESS }
}
