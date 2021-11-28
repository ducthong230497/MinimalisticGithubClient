package com.github.minimalisticclient._di.domain.modules

import com.github.minimalisticclient.domain.use_cases.GetUserReposUseCase
import com.github.minimalisticclient.domain.use_cases.GetUserReposUseCaseImpl
import com.github.minimalisticclient.domain.use_cases.GetUserUseCase
import com.github.minimalisticclient.domain.use_cases.GetUserUseCaseImpl
import org.koin.dsl.module

val domainDetailModule = module {
    factory<GetUserUseCase> { GetUserUseCaseImpl(userRepository = get()) }
    factory<GetUserReposUseCase> { GetUserReposUseCaseImpl(reposRepository = get()) }
}
