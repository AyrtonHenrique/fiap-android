package com.fiap.fiap_android_seguros.application.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import java.util.*

class MensagensViewModel (application: Application) : AndroidViewModel(application) {

    val mensagens: LiveData<List<Mensagem>>

    init {
        mensagens = MutableLiveData( buscaMensagens())
    }

    private fun buscaMensagens(): List<Mensagem> {
        val m1 = Mensagem("1","CARLOS",        "ceduardo.roque@gmail.com",        "Gostaria de saber em que pé está minha análise. Obrigado",        "JOAO")
        val m2 = Mensagem("2","AYRTON",        "ceduardo.roque@gmail.com",        "Gostaria de saber em que pé está minha análise. Obrigado",        "JOAO")
        val m3 = Mensagem("3","SARA",        "ceduardo.roque@gmail.com",        "Gostaria de saber em que pé está minha análise. Obrigado",        "JOAO")
        val listaTeste = LinkedList<Mensagem>(listOf(m1, m2, m3))
        return listaTeste
    }

}
