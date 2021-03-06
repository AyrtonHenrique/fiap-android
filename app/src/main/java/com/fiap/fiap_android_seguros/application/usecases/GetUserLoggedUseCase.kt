package com.fiap.fiap_android_seguros.application.usecases

import androidx.lifecycle.MutableLiveData
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.domain.entity.User
import com.fiap.fiap_android_seguros.domain.repositories.UserRepository

class GetUserLoggedUseCase(
    private val userRepository: UserRepository
) {
    suspend fun userInfo() : RequestState<User>{
        return userRepository.getUserLogged()
    }
}