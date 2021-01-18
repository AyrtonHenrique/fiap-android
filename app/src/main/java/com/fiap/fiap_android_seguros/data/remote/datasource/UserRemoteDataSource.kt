package com.fiap.fiap_android_seguros.data.remote.datasource

import com.fiap.fiap_android_seguros.data.remote.NewUserRemoteRequest
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.data.remote.UserLoginRemoteRequest
import com.fiap.fiap_android_seguros.domain.entity.Conversa
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import com.fiap.fiap_android_seguros.domain.entity.User

interface UserRemoteDataSource {
    suspend fun getUserLogged(): RequestState<User>

    suspend fun doLogin(userLogin: UserLoginRemoteRequest): RequestState<UserRemoteResponse>

    suspend fun create(newUser: NewUserRemoteRequest): RequestState<UserRemoteResponse>

    suspend fun sendMessage(message: Mensagem, conversation: String?): RequestState<Mensagem>

    suspend fun GetUsers(tipoCorretor: Boolean): RequestState<Array<User>>

    suspend fun getAllConversation(idUser: String, ehcorretor: Boolean): RequestState<List<Conversa>>

    suspend fun getUserById(id: String): RequestState<User>

    suspend fun removeConversation(id: String): RequestState<String?>

    suspend fun signOut(): RequestState<Void?>

}