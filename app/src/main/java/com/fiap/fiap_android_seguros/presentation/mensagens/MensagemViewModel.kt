package com.fiap.fiap_android_seguros.presentation.mensagens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.fiap_android_seguros.application.usecases.MessageUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import kotlinx.coroutines.launch

class MensagemViewModel(
    private val messageUseCase: MessageUseCase
) : ViewModel() {


    val messageState = MutableLiveData<RequestState<Mensagem>>()

    fun send(msg: String, idConversa: String) {
        viewModelScope.launch {
            messageState.value = messageUseCase.SendMessage(msg, idConversa, "")
        }
    }

    fun sendMessageUser(msg: String, idUser: String) {
        viewModelScope.launch {
            messageState.value = messageUseCase.SendMessage(msg, "", idUser)
        }
    }

}