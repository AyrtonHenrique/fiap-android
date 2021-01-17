package com.fiap.fiap_android_seguros.presentation.mensagens

import android.app.Application
import androidx.lifecycle.*
import com.fiap.fiap_android_seguros.application.usecases.MessageUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.domain.entity.Conversa
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class ConversasViewModel(private val messageUseCase: MessageUseCase) :
    ViewModel() {

    val mensagens = MutableLiveData<List<Conversa>>()

    fun buscaConversas() {
        viewModelScope.launch {
            var conversas = GlobalScope.async { messageUseCase.GetAllConversations() }.await()

            when (conversas) {
                is RequestState.Success -> {

                    mensagens.value = conversas.data

                }
                RequestState.Loading -> TODO()
                is RequestState.Error -> TODO()
            }
        }

    }

}
