package com.fiap.fiap_android_seguros.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.models.RequestState
import com.fiap.fiap_android_seguros.models.Usuario
import com.fiap.fiap_android_seguros.ui.login.LoginViewModel
import com.fiap.fiap_android_seguros.ui.usuario.NovoCadastroActivity
import com.fiap.fiap_android_seguros.ui.usuario.UsuarioActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicia escondendo o teclado
        hideKeyboard()

        startListeners()
        iniciarViewModel()
        iniciarObserver()

    }

    private fun iniciarObserver() {
        loginViewModel.loginState.observe(this, Observer {
            when(it) {
                is RequestState.Success -> {
                    startActivity((Intent(this, UsuarioActivity::class.java)))
                }
                is RequestState.Error -> {
                    tvFeedbackLogin.text = it.throwable.message
                }
                is RequestState.Loading -> {

                }
            }
        })


    }

    private fun iniciarViewModel() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

    }

    private fun startListeners() {
        btEntrar.setOnClickListener{
            loginViewModel.logar(Usuario(
                etEmail.text.toString(),
                    etSenha.text.toString()
            ))
        }


        tvNovoCadastro.setOnClickListener{
            startActivity((Intent(this, NovoCadastroActivity::class.java)))
        }
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