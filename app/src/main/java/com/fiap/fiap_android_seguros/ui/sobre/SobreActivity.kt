package com.fiap.fiap_android_seguros.ui.sobre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.ui.corretor.CorretorActivity
import com.fiap.fiap_android_seguros.ui.usuario.UsuarioActivity
import kotlinx.android.synthetic.main.activity_novo_cadastro.*
import kotlinx.android.synthetic.main.activity_sobre.*


class SobreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)
        startListeners()
        validaCorretor()
    }

    private fun validaCorretor() {
        // Valida se o login é de um corretor e adapta o front
        val origemCorretor = intent.getStringExtra("ORIGEM_CORRETOR")
        if(origemCorretor.equals("TRUE")) {
            ivCabecalho.setImageResource(R.drawable.corretor_header_simples)
            ivVoltar.setOnClickListener{
                startActivity(Intent(this, CorretorActivity::class.java))
                finish()
            }
        }

    }


    private fun startListeners() {
        ivVoltar.setOnClickListener{
            startActivity(Intent(this, UsuarioActivity::class.java))
            finish()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemUI()
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