package com.fiap.fiap_android_seguros.presentation.newUser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.fiap_android_seguros.application.usecases.CreateUserUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.domain.entity.User
import kotlinx.coroutines.launch

class NewUserViewModel(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {
    val registerState = MutableLiveData<RequestState<UserRemoteResponse>>()

    fun signOn(
        email: String,
        password: String,
        nome: String,
        address: String,
        idade: String,
        corretor: Boolean,
        cliente: Boolean
    ) {
        viewModelScope.launch {
            registerState.value = createUserUseCase.register(
                User(
                    nome,
                    email,
                    "",
                    password,
                    address,
                    corretor,
                    cliente,
                    idade
                )
            )
        }
    }
}