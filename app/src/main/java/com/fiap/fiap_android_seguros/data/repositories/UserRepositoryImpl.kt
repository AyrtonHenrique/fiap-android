package com.fiap.fiap_android_seguros.data.repositories

import com.fiap.fiap_android_seguros.data.remote.NewUserRemoteRequest
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserLoginRemoteRequest
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.data.remote.datasource.UserRemoteDataSource
import com.fiap.fiap_android_seguros.domain.entity.User
import com.fiap.fiap_android_seguros.domain.repositories.UserRepository
import com.google.android.gms.tasks.Tasks.await
import kotlinx.coroutines.runBlocking

class UserRepositoryImpl(
    val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getUserLogged(): RequestState<UserRemoteResponse> {
        return userRemoteDataSource.getUserLogged()
//        val user = userRemoteDataSource.getUserLogged()
//        runBlocking {
//            when (user) {
//                is RequestState.Success ->{
//                    user.data.name
//                }
//                is RequestState.Error -> {
//                }
//            }
//            return@runBlocking RequestState.Success(User("as"))
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
                newUser.cliente,
                newUser.idade
            )
        )
    }
}