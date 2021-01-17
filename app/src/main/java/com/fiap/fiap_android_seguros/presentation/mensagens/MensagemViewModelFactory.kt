package com.fiap.fiap_android_seguros.presentation.mensagens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiap.fiap_android_seguros.application.usecases.MessageUseCase

class MensagemViewModelFactory(
    private val SendMessageUseCase: MessageUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MessageUseCase::class.java).newInstance(SendMessageUseCase)
    }
}