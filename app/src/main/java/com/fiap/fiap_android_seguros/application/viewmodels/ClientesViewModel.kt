package com.fiap.fiap_android_seguros.application.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import com.fiap.fiap_android_seguros.domain.entity.User
import java.util.*

class ClientesViewModel (application: Application) : AndroidViewModel(application) {

    val clientes: LiveData<List<User>>

    init {
        clientes = MutableLiveData( buscaClientes())
    }

    private fun buscaClientes(): List<User> {
        val u1 = User("Giacomo")
        val u2 = User("Giuseppe")
        val u3 = User("Giovanni")
        val listaTeste = LinkedList<User>(listOf(u1, u2, u3))
        return listaTeste
    }

}
