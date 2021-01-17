package com.fiap.fiap_android_seguros.presentation.clientes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.fiap_android_seguros.application.usecases.GetAllClientesUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.domain.entity.User
import kotlinx.coroutines.launch
import java.util.*

class ClientesViewModel(
    private val getAllClientesUseCase: GetAllClientesUseCase
) : ViewModel() {
    val getAllTypeClienteState = MutableLiveData<RequestState<Array<User>>>()
    val clientes = MutableLiveData<List<User>>()

    private fun buscaClientes(): List<User> {
        val u1 = User("Giacomo")
        val u2 = User("Giuseppe")
        val u3 = User("Giovanni")

        return LinkedList<User>(listOf(u1, u2, u3))
    }

    fun getAllTypeCliente() {
        viewModelScope.launch {
            getAllTypeClienteState.value = getAllClientesUseCase.get()
        }
    }
}