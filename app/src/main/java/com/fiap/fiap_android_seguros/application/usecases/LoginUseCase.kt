package com.fiap.fiap_android_seguros.application.usecases

import com.fiap.fiap_android_seguros.application.viewmodels.UserLoginRequest
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse

import com.fiap.fiap_android_seguros.domain.entity.User
import com.fiap.fiap_android_seguros.domain.repositories.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class LoginUseCase(
    private val userRepository: UserRepository
) {

    suspend fun doLogin(userLogin: UserLoginRequest): RequestState<User>? {
        val logou =
            GlobalScope.async { userRepository.doLogin(User(userLogin.email, userLogin.password)) }
                .await()

        when (logou) {
            is RequestState.Success -> {
                return userRepository.getUserLogged()
            }
            RequestState.Loading -> TODO()
            is RequestState.Error -> TODO()
        }
    }

    suspend fun signOut(): RequestState<Void?> {
        return userRepository.signout()
    }

}