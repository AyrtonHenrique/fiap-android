package com.fiap.fiap_android_seguros.presentation.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.fiap_android_seguros.application.usecases.CreateUserUseCase
import com.fiap.fiap_android_seguros.application.usecases.GetUserByIdUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.domain.entity.User
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {
    val userState = MutableLiveData<RequestState<User>>()

    fun get(
        id: String
    ) {
        viewModelScope.launch {
            userState.value = getUserByIdUseCase.get(id)
        }
    }
}