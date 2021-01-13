package com.fiap.fiap_android_seguros.ui.corretor

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.application.viewmodels.ClientesViewModel
import com.fiap.fiap_android_seguros.application.viewmodels.MensagensViewModel
import com.fiap.fiap_android_seguros.ui.mensagens.MensagemListaAdapter
import com.fiap.fiap_android_seguros.ui.usuario.UsuarioActivity
import kotlinx.android.synthetic.main.activity_mensagens_enviadas.*
import kotlinx.android.synthetic.main.activity_meus_clientes.*

class MeusClientesActivity : AppCompatActivity() {

    private lateinit var clientesViewModel: ClientesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meus_clientes)
        startListeners()
        carregaRecyclerView()
    }

    private fun carregaRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvMeusClientes)
        val adapter = ClientesListaAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        clientesViewModel = ViewModelProvider(this).get(ClientesViewModel::class.java)
        clientesViewModel.clientes.observe(this, Observer { clientes ->
            clientes?.let { adapter.setClientes(it) }
        })
    }


    private fun startListeners() {
        ivVoltarMeusClientes.setOnClickListener {
            startActivity(Intent(this, CorretorActivity::class.java))
        }
    }

}