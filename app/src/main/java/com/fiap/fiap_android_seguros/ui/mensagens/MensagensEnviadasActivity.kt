package com.fiap.fiap_android_seguros.ui.mensagens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.application.viewmodels.MensagensViewModel
import com.fiap.fiap_android_seguros.ui.usuario.FalarCorretorActivity
import com.fiap.fiap_android_seguros.ui.usuario.UsuarioActivity
import kotlinx.android.synthetic.main.activity_mensagens_enviadas.*
import kotlinx.android.synthetic.main.activity_usuario.*

class MensagensEnviadasActivity : AppCompatActivity() {

    private lateinit var mensagensViewModel: MensagensViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensagens_enviadas)
        startListeners()

        val recyclerView = findViewById<RecyclerView>(R.id.rvMensagens)
        val adapter = MensagemListaAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        mensagensViewModel = ViewModelProvider(this).get(MensagensViewModel::class.java)
        mensagensViewModel.mensagens.observe(this, Observer { mensagens ->
            mensagens?.let { adapter.setMensagens(it) }
        })
    }

    private fun startListeners() {

        ivVoltar2.setOnClickListener {
            startActivity(Intent(this, UsuarioActivity::class.java))
        }
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