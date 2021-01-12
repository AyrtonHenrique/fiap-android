package com.fiap.fiap_android_seguros.data.remote

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class NewUserFirebasePayload(
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val idade: String? = null,
    val corretor: Boolean? = null,
    val cliente: Boolean? = null,
    @get:Exclude val password: String? = null
)