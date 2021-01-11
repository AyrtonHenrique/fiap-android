package com.fiap.fiap_android_seguros.data.remote.datasource

import com.fiap.fiap_android_seguros.data.remote.RequestState

interface AppRemoteDataSource {
    suspend fun getMinVersionApp(): RequestState<Int>
}