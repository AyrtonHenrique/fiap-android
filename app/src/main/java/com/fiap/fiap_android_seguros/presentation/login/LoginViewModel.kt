package com.fiap.fiap_android_seguros.presentation.login

import  androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.fiap_android_seguros.application.usecases.LoginUseCase
import com.fiap.fiap_android_seguros.application.viewmodels.UserLoginRequest
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.domain.entity.User
import kotlinx.coroutines.launch


class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {


    val loginState = MutableLiveData<RequestState<User>>()
    val signOutState = MutableLiveData<RequestState<Void?>>()

    fun doLogin(email: String, senha: String) {
        viewModelScope.launch {
            loginState.value = loginUseCase.doLogin(UserLoginRequest(email, senha))
        }
    }

    fun signOut(){
        viewModelScope.launch {
            signOutState.value = loginUseCase.signOut()
        }
    }

}
