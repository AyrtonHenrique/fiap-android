package com.fiap.fiap_android_seguros.ui.corretor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.application.usecases.GetAllClientesUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.fiap_android_seguros.data.repositories.UserRepositoryImpl
import com.fiap.fiap_android_seguros.presentation.clientes.ClientesViewModel
import com.fiap.fiap_android_seguros.presentation.clientes.ClientesViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_pesquisar_clientes.*

class PesquisarClientesActivity : AppCompatActivity() {

    //    private lateinit var clientesViewModel: ClientesViewModel
    private val clientesViewModel: ClientesViewModel by lazy {
        ViewModelProvider(
            this,
            ClientesViewModelFactory(
                GetAllClientesUseCase(
                    UserRepositoryImpl(
                        (UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        ))
                    )
                )
            )
        ).get(ClientesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisar_clientes)

        tvPesquisarClientes.text =  intent.getStringExtra("NOME_CORRETOR_LOGADO").toString()
        textView18Pesquisar.text =  intent.getStringExtra("IDADE_CORRETOR_LOGADO").toString()

        startListeners()
        carregaRecyclerView()
    }

    private fun startListeners() {
        ivVoltarPesquisarClientes.setOnClickListener {
            startActivity(Intent(this, CorretorActivity::class.java))
            finish()
        }
    }

    private fun carregaRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvPesquisarClientes)
        val adapter = ClientesListaAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
//        clientesViewModel = ViewModelProvider(this).get(ClientesViewModel::class.java)
        clientesViewModel.getAllTypeCliente()
        clientesViewModel.getAllTypeClienteState.observe(this, Observer { clientes ->
            when (clientes) {
                is RequestState.Success -> {
                    clientes.data?.let { adapter.setClientes(it.toList()) }
                }
                RequestState.Loading -> TODO()
                is RequestState.Error -> TODO()
            }

        })
    }


    private fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                //or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Este item remove a barra superior
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

}