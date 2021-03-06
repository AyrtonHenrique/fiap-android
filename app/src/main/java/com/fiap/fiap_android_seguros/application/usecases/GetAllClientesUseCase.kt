package com.fiap.fiap_android_seguros.application.usecases

import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.domain.entity.User
import com.fiap.fiap_android_seguros.domain.repositories.UserRepository

class GetAllClientesUseCase (
    private val userRepository: UserRepository
) {

    suspend fun get(): RequestState<Array<User>> {
        return userRepository.getAllClientes()
    }
}