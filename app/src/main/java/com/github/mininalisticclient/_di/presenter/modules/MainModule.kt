package com.github.mininalisticclient._di.presenter.modules

import com.github.mininalisticclient.presenter.main_activity.MainViewModel
import com.github.mininalisticclient.presenter.search_result.SearchResultViewModel
import com.github.mininalisticclient.presenter.user_detail.UserDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val mainModule: Module = module {
    viewModel { MainViewModel() }
    viewModel { (query: String) -> SearchResultViewModel(query, userRepository = get()) }
    viewModel { (userLogin: String) -> UserDetailViewModel(userLogin, getUserUseCase = get(), getUserReposUseCase = get()) }
}
