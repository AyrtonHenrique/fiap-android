package com.fiap.fiap_android_seguros.data.remote

import com.google.firebase.firestore.DocumentId

data class ConversationRequest(
    @DocumentId
    val id: String? = null,
    val messages: ArrayList<MessageRequest>? = null,
    val idCorretor : String? = null,
    val idCliente: String? = null,
    val nomeCliente: String? = null,
    val nomeCorretor: String? = null
) {
}