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
    suspend fun SendMessage(
        msg: String,
        idConversa: String,
        idUser: String
    ): RequestState<Mensagem> {

        val obterDadosUsuarioLogado = GlobalScope.async { getUserLoggedUseCase.userInfo() }.await()
        val obterCorretores = GlobalScope.async { userRepository.getAllCorretores() }

        when (obterDadosUsuarioLogado) {
            is RequestState.Success -> {
                val usuarioLogado = obterDadosUsuarioLogado.data
                if (idConversa != "") {
                    return userRepository.sendMessage(
                        Mensagem(
                            usuarioLogado.id,
                            msg,
                            "",
                            usuarioLogado.corretor,
                            "",
                            usuarioLogado.name
                        ),
                        idConversa
                    )
                }

                if (idUser != "") {
                    val obterDadosUsuarioEnviado =
                        GlobalScope.async { userRepository.getUserById(idUser) }.await()
                    when (obterDadosUsuarioEnviado) {
                        is RequestState.Success -> {
                            return userRepository.sendMessage(
                                Mensagem(
                                    usuarioLogado.id,
                                    msg,
                                    idUser,
                                    usuarioLogado.corretor,
                                    obterDadosUsuarioEnviado.data.name,
                                    usuarioLogado.name
                                ),
                                ""
                            )
                        }
                        RequestState.Loading -> TODO()
                        is RequestState.Error -> TODO()
                    }

                }

                var corretores = obterCorretores.await()
                when (corretores) {
                    is RequestState.Success -> {
                        var corretorSelecionado = corretores.data.random()

                        return userRepository.sendMessage(
                            Mensagem(
                                usuarioLogado.id,
                                msg,
                                corretorSelecionado.id,
                                usuarioLogado.corretor,
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

    suspend fun RemoveConversation(id: String): RequestState<String?> {
        return userRepository.removeConversation(id)
    }

}