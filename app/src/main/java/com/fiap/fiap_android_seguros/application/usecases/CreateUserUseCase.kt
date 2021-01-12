package com.fiap.fiap_android_seguros.application.usecases

import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.domain.entity.User
import com.fiap.fiap_android_seguros.domain.repositories.UserRepository

class CreateUserUseCase(
    private val userRepository: UserRepository
) {

    suspend fun register(user: User): RequestState<UserRemoteResponse>? {
        return userRepository.create(user)
    }
}