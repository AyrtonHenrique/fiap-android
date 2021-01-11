package com.fiap.fiap_android_seguros.application.usecases

import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.domain.repositories.AppRepository

class GetMinAppVersionUseCase(
    private val appRespository: AppRepository
) {
    suspend fun getMinVersionApp(): RequestState<Int> =
        appRespository.getMinVersionApp()
}