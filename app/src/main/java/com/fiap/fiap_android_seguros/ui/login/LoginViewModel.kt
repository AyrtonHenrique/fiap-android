package com.fiap.fiap_android_seguros.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiap.fiap_android_seguros.models.RequestState
import com.fiap.fiap_android_seguros.models.Usuario
import com.fiap.fiap_android_seguros.repository.UsuarioRepository

class LoginViewModel : ViewModel() {

    private val usuarioRepository = UsuarioRepository()

    val loginState = MutableLiveData<RequestState<String>>()

    fun logar (usuario: Usuario) {
        loginState.value = usuarioRepository.logar(usuario).value
    }

}
