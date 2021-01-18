package com.fiap.fiap_android_seguros.domain.repositories

import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserLoginRemoteRequest
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.domain.entity.Conversa
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import com.fiap.fiap_android_seguros.domain.entity.User

interface UserRepository {
    suspend fun getUserLogged(): RequestState<User>

    suspend fun doLogin(userLogin: User): RequestState<UserRemoteResponse>

    suspend fun create(newUser: User): RequestState<UserRemoteResponse>

    suspend fun sendMessage(message: Mensagem, conversation: String?) :RequestState<Mensagem>

    suspend fun getAllCorretores() : RequestState<Array<User>>

    suspend fun getAllClientes() : RequestState<Array<User>>

    suspend fun getAllConversations() : RequestState<List<Conversa>>

    suspend fun getUserById(id: String) : RequestState<User>

    suspend fun removeConversation(id : String) : RequestState<String?>

    suspend fun signout() : RequestState<Void?>
}