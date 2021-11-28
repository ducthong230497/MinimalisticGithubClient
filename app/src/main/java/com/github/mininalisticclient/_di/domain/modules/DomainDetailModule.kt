package com.github.mininalisticclient._di.domain.modules

import com.github.mininalisticclient.domain.use_cases.GetUserReposUseCase
import com.github.mininalisticclient.domain.use_cases.GetUserReposUseCaseImpl
import com.github.mininalisticclient.domain.use_cases.GetUserUseCase
import com.github.mininalisticclient.domain.use_cases.GetUserUseCaseImpl
import org.koin.dsl.module

val domainDetailModule = module {
    factory<GetUserUseCase> { GetUserUseCaseImpl(userRepository = get()) }
    factory<GetUserReposUseCase> { GetUserReposUseCaseImpl(reposRepository = get()) }
}
