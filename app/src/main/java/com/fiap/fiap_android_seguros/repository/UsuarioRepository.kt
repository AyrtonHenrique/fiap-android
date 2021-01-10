package com.fiap.fiap_android_seguros.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fiap.fiap_android_seguros.models.RequestState
import com.fiap.fiap_android_seguros.models.Usuario

class UsuarioRepository {

    fun logar (usuario: Usuario) : LiveData<RequestState<String>> {

        val response = MutableLiveData<RequestState<String>>()
        response.value = RequestState.Loading

        if (usuario.email.equals("ceduardo.roque@gmail.com") && usuario.senha.equals("123")) {
            response.value = RequestState.Success("")
        } else {
            response.value = RequestState.Error(Exception("Usuário ou senha inválidos"))
        }

        return response
    }

}