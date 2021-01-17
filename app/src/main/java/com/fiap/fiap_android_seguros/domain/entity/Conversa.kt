package com.fiap.fiap_android_seguros.domain.entity

import kotlinx.android.parcel.Parcelize


data class Conversa (
    var id: String,
    var mensagagens: List<Mensagem>,
    var idCorretor: String,
    var idCliente: String,
    val nomeCliente: String,
    val nomeCorretor: String
) {
}