package com.fiap.fiap_android_seguros.domain.repositories

import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserLoginRemoteRequest
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.domain.entity.User

interface UserRepository {
    suspend fun getUserLogged(): RequestState<UserRemoteResponse>

    suspend fun doLogin(userLogin: User): RequestState<UserRemoteResponse>

    suspend fun create(newUser: User): RequestState<UserRemoteResponse>
}