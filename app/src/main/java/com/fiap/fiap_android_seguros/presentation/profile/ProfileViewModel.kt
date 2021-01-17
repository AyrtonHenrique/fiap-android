package com.fiap.fiap_android_seguros.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.fiap_android_seguros.application.usecases.GetUserLoggedUseCase
import com.fiap.fiap_android_seguros.application.usecases.LoginUseCase
import com.fiap.fiap_android_seguros.application.viewmodels.UserLoginRequest
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.domain.entity.User
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserLoggedUseCase: GetUserLoggedUseCase
) : ViewModel() {
    val userState = MutableLiveData<RequestState<User>>()

    fun getInfoUser() {
        viewModelScope.launch {
            userState.value = getUserLoggedUseCase.userInfo()
        }
    }

}