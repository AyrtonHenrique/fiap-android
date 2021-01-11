package com.fiap.fiap_android_seguros.data.repositories

import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.datasource.AppRemoteDataSource
import com.fiap.fiap_android_seguros.domain.repositories.AppRepository

class AppRepositoryImpl (
    private val appRemoteDataSource: AppRemoteDataSource
) : AppRepository {
    override suspend fun getMinVersionApp(): RequestState<Int> {
        return appRemoteDataSource.getMinVersionApp()
    }
}