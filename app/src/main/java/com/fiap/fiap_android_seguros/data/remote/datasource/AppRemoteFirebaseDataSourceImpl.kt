package com.fiap.fiap_android_seguros.data.remote.datasource

import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.extensions.fromRemoteConfig
import com.google.gson.Gson

class AppRemoteFirebaseDataSourceImpl : AppRemoteDataSource {
    override suspend fun getMinVersionApp(): RequestState<Int> {
        val minVersion = Gson().fromRemoteConfig("min_version_app", Int::class.java) ?: 0

        return RequestState.Success(minVersion)
    }
}