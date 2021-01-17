package com.fiap.fiap_android_seguros.application.usecases

import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.domain.entity.User
import com.fiap.fiap_android_seguros.domain.repositories.UserRepository

class GetUserByIdUseCase(
    private val userRepository: UserRepository
) {

    suspend fun get(id: String): RequestState<User> {
        return userRepository.getUserById(id)
    }
}