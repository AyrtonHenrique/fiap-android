package com.fiap.fiap_android_seguros.application.usecases

import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.domain.entity.Conversa
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import com.fiap.fiap_android_seguros.domain.repositories.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MessageUseCase(
    private val userRepository: UserRepository,
    private val getUserLoggedUseCase: GetUserLoggedUseCase
) {
    suspend fun SendUserMessage(msg: String, idConversa: String?) {

    }

    suspend fun SendCorretorMessage(msg: String, idConversa: String): RequestState<Void?> {

        val obterDadosUsuarioLogado = GlobalScope.async { getUserLoggedUseCase.userInfo() }.await()
        val obterCorretores = GlobalScope.async { userRepository.getAllCorretores() }

        when (obterDadosUsuarioLogado) {
            is RequestState.Success -> {

                var corretores = obterCorretores.await()
                when (corretores) {
                    is RequestState.Success -> {

                        val usuarioLogado = obterDadosUsuarioLogado.data

                        var corretorSelecionado = corretores.data.random()

                        return userRepository.sendMessage(
                            Mensagem(
                                usuarioLogado.id,
                                msg,
                                corretorSelecionado.id,
                                false,
                                corretorSelecionado.name,
                                usuarioLogado.name
                            ),
                            ""
                        )
                    }
                    RequestState.Loading -> TODO()
                    is RequestState.Error -> TODO()
                }
            }
            RequestState.Loading -> TODO()
            is RequestState.Error -> TODO()
        }


    }

    suspend fun GetAllConversations(): RequestState<List<Conversa>> {
        return userRepository.getAllConversations()
    }

}