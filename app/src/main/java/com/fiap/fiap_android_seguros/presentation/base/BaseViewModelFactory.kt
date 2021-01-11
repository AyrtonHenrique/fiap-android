package com.fiap.fiap_android_seguros.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiap.fiap_android_seguros.application.usecases.GetMinAppVersionUseCase

class BaseViewModelFactory(
    private val getMinAppVersionUseCase: GetMinAppVersionUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetMinAppVersionUseCase::class.java)
            .newInstance(getMinAppVersionUseCase)
    }
}