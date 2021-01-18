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
                            ""
                        ),
                        idConversa
                    )
                }

                if (idUser != "") {
                    val obterDadosUsuarioEnviado =
                        GlobalScope.async { userRepository.getUserById(idUser) }.await()
                    when (obterDadosUsuarioEnviado) {
                        is RequestState.Success -> {
                            val usr = obterDadosUsuarioEnviado.data
                            val nomeCorretor =
                                if (usuarioLogado.corretor) usuarioLogado.name else usr.name
                            val nomeCliente =
                                if (!usuarioLogado.corretor) usuarioLogado.name else usr.name

                            return userRepository.sendMessage(
                                Mensagem(
                                    usuarioLogado.id,
                                    msg,
                                    idUser,
                                    usuarioLogado.corretor,
                                    nomeCorretor,
                                    nomeCliente
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
        val obterDadosUsuarioLogado = GlobalScope.async { getUserLoggedUseCase.userInfo() }.await()
        when (obterDadosUsuarioLogado) {
            is RequestState.Success -> {
                val usuario = obterDadosUsuarioLogado.data
                return userRepository.getAllConversations(usuario.id, usuario.corretor)
            }
            RequestState.Loading -> TODO()
            is RequestState.Error -> TODO()
        }
    }

    suspend fun RemoveConversation(id: String): RequestState<String?> {
        return userRepository.removeConversation(id)
    }

}