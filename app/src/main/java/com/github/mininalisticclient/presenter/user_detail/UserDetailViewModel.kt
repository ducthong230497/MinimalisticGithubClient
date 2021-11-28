package com.github.mininalisticclient.presenter.user_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.mininalisticclient.domain.use_cases.GetUserReposUseCase
import com.github.mininalisticclient.domain.use_cases.GetUserUseCase

class UserDetailViewModel(
    userLogin: String,
    getUserUseCase: GetUserUseCase,
    getUserReposUseCase: GetUserReposUseCase
): ViewModel() {
    val userDetail = getUserUseCase.execute(userLogin).asLiveData(viewModelScope.coroutineContext)
    val userRepos = getUserReposUseCase.execute(userLogin).asLiveData(viewModelScope.coroutineContext)
}
