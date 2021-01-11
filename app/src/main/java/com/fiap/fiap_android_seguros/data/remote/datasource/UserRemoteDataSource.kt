package com.fiap.fiap_android_seguros.data.remote.datasource

import com.fiap.fiap_android_seguros.data.remote.NewUserRemoteRequest
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.data.remote.UserLoginRemoteRequest

interface UserRemoteDataSource {
    suspend fun getUserLogged(): RequestState<UserRemoteResponse>

    suspend fun doLogin(userLogin: UserLoginRemoteRequest): RequestState<UserRemoteResponse>

    suspend fun create(newUser: NewUserRemoteRequest): RequestState<UserRemoteResponse>
}