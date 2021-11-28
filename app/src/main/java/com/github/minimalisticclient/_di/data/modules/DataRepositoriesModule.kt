package com.github.minimalisticclient._di.data.modules

import com.github.minimalisticclient.data.repositories_impl.ReposRepositoryImpl
import com.github.minimalisticclient.data.repositories_impl.UserRepositoryImpl
import com.github.minimalisticclient.domain.repositories.ReposRepository
import com.github.minimalisticclient.domain.repositories.UserRepository
import org.koin.dsl.module

val dataRepositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(apis = get(), userDao = get(), database = get(), gson = get()) }
    single<ReposRepository> { ReposRepositoryImpl(apis = get(), repoDao = get(), database = get()) }
}
