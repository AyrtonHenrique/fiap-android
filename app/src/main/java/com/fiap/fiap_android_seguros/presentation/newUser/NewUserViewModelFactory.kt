package com.fiap.fiap_android_seguros.presentation.newUser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiap.fiap_android_seguros.application.usecases.CreateUserUseCase

class NewUserViewModelFactory (
    private val loginUseCase: CreateUserUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CreateUserUseCase::class.java).newInstance(loginUseCase)
    }
}