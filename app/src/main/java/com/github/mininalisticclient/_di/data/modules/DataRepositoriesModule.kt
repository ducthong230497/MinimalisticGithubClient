package com.github.mininalisticclient._di.data.modules

import com.github.mininalisticclient.data.repositories_impl.ReposRepositoryImpl
import com.github.mininalisticclient.data.repositories_impl.UserRepositoryImpl
import com.github.mininalisticclient.domain.repositories.ReposRepository
import com.github.mininalisticclient.domain.repositories.UserRepository
import org.koin.dsl.module

val dataRepositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(apis = get(), userDao = get(), database = get()) }
    single<ReposRepository> { ReposRepositoryImpl(apis = get(), repoDao = get(), database = get()) }
}
