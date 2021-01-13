package com.fiap.fiap_android_seguros.ui.usuario

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.ui.login.LoginActivity
import com.fiap.fiap_android_seguros.ui.main.MainActivity
import com.fiap.fiap_android_seguros.ui.mensagens.MensagensEnviadasActivity
import com.fiap.fiap_android_seguros.ui.sobre.SobreActivity
import kotlinx.android.synthetic.main.activity_usuario.*

class UsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)
        startListeners()
    }

    private fun startListeners() {
        btSair.setOnClickListener{
            showDialog()
        }
        btSobre.setOnClickListener{
            startActivity(Intent(this, SobreActivity::class.java))
        }

        ivFalarCorretor.setOnClickListener {
            startActivity(Intent(this, FalarCorretorActivity::class.java))
        }
        tvFalarComUmCorretor.setOnClickListener {
            startActivity(Intent(this, FalarCorretorActivity::class.java))
        }

        ivPesquisarPlanos.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        tvPesquisarPlano.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        ivGerenciarMensagens.setOnClickListener {
            startActivity(Intent(this, MensagensEnviadasActivity::class.java))
        }
        tvGerenciarMensagens.setOnClickListener {
            startActivity(Intent(this, MensagensEnviadasActivity::class.java))
        }
    }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showDialog() {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Fechar Aplicativo")
        builder.setMessage("Tem certeza que gostaria de fechar a sua sessão?")

        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
        builder.setPositiveButton("Sim", dialogClickListener)
        builder.setNeutralButton("Cancelar", dialogClickListener)

        dialog = builder.create()
        dialog.show()
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