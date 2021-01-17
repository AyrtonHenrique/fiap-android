package com.fiap.fiap_android_seguros.data.remote

data class MessageRequest(
    val corretor: Boolean? = null,
    val textoMensagem: String? = null,
    val idRemetente : String? = null
) {}