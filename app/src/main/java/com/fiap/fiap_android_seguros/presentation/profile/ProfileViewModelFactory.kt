package com.fiap.fiap_android_seguros.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiap.fiap_android_seguros.application.usecases.CreateUserUseCase
import com.fiap.fiap_android_seguros.application.usecases.GetUserLoggedUseCase

class ProfileViewModelFactory(
    private val loggedUserCase: GetUserLoggedUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetUserLoggedUseCase::class.java).newInstance(loggedUserCase)
    }
}