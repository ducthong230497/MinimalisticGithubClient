package com.github.minimalisticclient._di.presenter.modules

import com.github.minimalisticclient.presenter.search_result.SearchResultViewModel
import com.github.minimalisticclient.presenter.user_detail.UserDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val mainModule: Module = module {
    viewModel { (query: String) -> SearchResultViewModel(query, userRepository = get()) }
    viewModel { (userLogin: String) -> UserDetailViewModel(userLogin, getUserUseCase = get(), getUserReposUseCase = get()) }
}
