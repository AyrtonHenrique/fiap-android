package com.fiap.fiap_android_seguros.data.repositories

import com.fiap.fiap_android_seguros.data.remote.NewUserRemoteRequest
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserLoginRemoteRequest
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.data.remote.datasource.UserRemoteDataSource
import com.fiap.fiap_android_seguros.domain.entity.Conversa
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import com.fiap.fiap_android_seguros.domain.entity.User
import com.fiap.fiap_android_seguros.domain.repositories.UserRepository

class UserRepositoryImpl(
    val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getUserLogged(): RequestState<User> {
        return userRemoteDataSource.getUserLogged()
    }

    override suspend fun doLogin(userLogin: User): RequestState<UserRemoteResponse> {
        return userRemoteDataSource
            .doLogin(UserLoginRemoteRequest(userLogin.email, userLogin.password))
    }

    override suspend fun create(newUser: User): RequestState<UserRemoteResponse> {
        return userRemoteDataSource.create(
            NewUserRemoteRequest(
                newUser.name,
                newUser.email,
                newUser.phone,
                newUser.password,
                newUser.address,
                newUser.corretor,
                newUser.idade
            )
        )
    }

    override suspend fun sendMessage(
        message: Mensagem,
        conversation: String?
    ): RequestState<Mensagem> {
        return userRemoteDataSource.sendMessage(message, conversation)
    }

    override suspend fun getAllCorretores(): RequestState<Array<User>> {
        return userRemoteDataSource.GetUsers(true)
    }

    override suspend fun getAllClientes(): RequestState<Array<User>> {
        return userRemoteDataSource.GetUsers(false)
    }

    override suspend fun getAllConversations(
        idUser: String,
        ehCorretor: Boolean
    ): RequestState<List<Conversa>> {
        return userRemoteDataSource.getAllConversation(idUser, ehCorretor)
    }

    override suspend fun getUserById(id: String): RequestState<User> {
        return userRemoteDataSource.getUserById(id)
    }

    override suspend fun removeConversation(id: String): RequestState<String?> {
        return userRemoteDataSource.removeConversation(id)
    }

    override suspend fun signout(): RequestState<Void?> {
        return userRemoteDataSource.signOut()
    }

    suspend fun getMessagesConversation(conversation: String?) {
        TODO("vai fazer ainda sapora")
    }
}