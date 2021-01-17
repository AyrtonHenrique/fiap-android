package com.fiap.fiap_android_seguros.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Mensagem(
    val IdCliente: String,
    var textoMensagem: String,
    var idCorretor: String,
    var enviadoPeloCorretor: Boolean,
    val nomeCorretor : String,
    val nomeCliente: String
) : Parcelable {

}