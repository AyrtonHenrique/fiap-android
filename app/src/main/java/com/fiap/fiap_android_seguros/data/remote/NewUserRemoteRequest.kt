package com.fiap.fiap_android_seguros.data.remote

data class NewUserRemoteRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String
)