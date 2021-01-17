package com.fiap.fiap_android_seguros.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiap.fiap_android_seguros.application.usecases.CreateUserUseCase
import com.fiap.fiap_android_seguros.application.usecases.GetUserByIdUseCase

class UsuarioViewModelFactory(
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetUserByIdUseCase::class.java)
            .newInstance(getUserByIdUseCase)
    }
}