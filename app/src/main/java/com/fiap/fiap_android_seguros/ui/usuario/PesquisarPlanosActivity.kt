package com.fiap.fiap_android_seguros.ui.usuario

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_pesquisar_planos.*

class PesquisarPlanosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisar_planos)
        startListeners()
    }

    private fun startListeners() {
        ivVoltarPesquisarPlanos.setOnClickListener {
            startActivity(Intent(this, UsuarioActivity::class.java))
            finish()
        }

        ivAmil.setOnClickListener {
            showDialog("Amil")
        }
        ivUnimed.setOnClickListener {
            showDialog("Unimed")
        }
        ivBradesco.setOnClickListener {
            showDialog("Bradesco Saúde")
        }
    }


    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun showDialog(plano: String) {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Escolher Plano")
        builder.setMessage("Tem certeza que gostaria de escolher o plano " + plano
                + "? \nO mesmo terá validade até o último dia deste ano.")

        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    toast("Plano da " + plano + " escolhido.")
                    Handler().postDelayed({
                        startActivity(Intent(this, UsuarioActivity::class.java))
                        // Finaliza a Activity Atual
                        finish()
                    }, 3000)
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