package com.fiap.fiap_android_seguros.domain.entity

data class Mensagem ( val idMensagem: String,
                      val remetente: String,
                      val emailRemetente: String,
                      val textoMensagem: String,
                      val destinatario: String) {

}