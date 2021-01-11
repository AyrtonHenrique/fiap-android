package com.fiap.fiap_android_seguros.application.usecases

import com.fiap.fiap_android_seguros.application.viewmodels.UserLoginRequest
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse

import com.fiap.fiap_android_seguros.domain.entity.User
import com.fiap.fiap_android_seguros.domain.repositories.UserRepository

class LoginUseCase (
    private val userRepository: UserRepository
) {

    suspend fun doLogin(userLogin: UserLoginRequest): RequestState<UserRemoteResponse>? {
        return userRepository.doLogin(User(userLogin.email,userLogin.password))
    }

}