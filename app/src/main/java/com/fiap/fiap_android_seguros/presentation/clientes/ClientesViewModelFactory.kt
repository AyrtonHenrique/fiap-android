package com.fiap.fiap_android_seguros.presentation.clientes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiap.fiap_android_seguros.application.usecases.GetAllClientesUseCase
import com.fiap.fiap_android_seguros.application.usecases.GetUserByIdUseCase

class ClientesViewModelFactory(
    private val getAllClientesUseCase: GetAllClientesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetAllClientesUseCase::class.java)
            .newInstance(getAllClientesUseCase)
    }
}