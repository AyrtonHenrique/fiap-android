package com.fiap.fiap_android_seguros.data.remote.mapper

import com.fiap.fiap_android_seguros.data.remote.NewUserFirebasePayload
import com.fiap.fiap_android_seguros.data.remote.NewUserRemoteRequest
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.domain.entity.User

object NewUserFirebasePayloadMapper {

    fun mapToNewUser(newUserFirebasePayload: NewUserFirebasePayload) = User(
        name = newUserFirebasePayload.name ?: "",
        email = newUserFirebasePayload.email ?: "",
        phone = newUserFirebasePayload.phone ?: "",
        password = newUserFirebasePayload.password ?: "",
        address = newUserFirebasePayload.address ?: "",
        idade = newUserFirebasePayload.idade ?: "",
        corretor = newUserFirebasePayload.corretor ?: false,
        cliente = newUserFirebasePayload.cliente ?: false


    )

    fun mapToNewUserFirebasePayload(newUser: NewUserRemoteRequest) = NewUserFirebasePayload(
        name = newUser.name,
        email = newUser.email,
        phone = newUser.phone,
        password = newUser.password,
        address = newUser.address ?: "",
        idade = newUser.idade ?: "",
        corretor = newUser.corretor ?: false,
        cliente = newUser.cliente ?: false
    )

    fun mapToUser(newUserFirebasePayload: NewUserFirebasePayload) = UserRemoteResponse(
        name = newUserFirebasePayload.name ?: ""
    )
}