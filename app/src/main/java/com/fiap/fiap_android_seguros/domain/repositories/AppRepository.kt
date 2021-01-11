package com.fiap.fiap_android_seguros.domain.repositories

import com.fiap.fiap_android_seguros.data.remote.RequestState

interface AppRepository {
    suspend fun getMinVersionApp(): RequestState<Int>
}