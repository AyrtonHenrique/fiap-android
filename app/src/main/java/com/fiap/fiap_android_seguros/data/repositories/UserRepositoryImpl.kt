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
import com.google.android.gms.tasks.Tasks.await
import kotlinx.coroutines.runBlocking

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

    override suspend fun sendMessage(message: Mensagem, conversation: String?): RequestState<Void?> {
        return userRemoteDataSource.sendMessage(message,conversation)
    }

    override suspend fun getAllCorretores(): RequestState<Array<User>> {
        return userRemoteDataSource.getUsersCorretor()
    }

    override suspend fun getAllConversations(): RequestState<List<Conversa>> {
        return userRemoteDataSource.getAllConversation()
    }

    override suspend fun getUserById(id: String): RequestState<User> {
        return userRemoteDataSource.getUserById(id)
    }

    override suspend fun removeConversation(id: String): RequestState<String?> {
        return userRemoteDataSource.removeConversation(id)
    }

    suspend fun getMessagesConversation(conversation: String?){
        TODO("vai fazer ainda sapora")
    }
}