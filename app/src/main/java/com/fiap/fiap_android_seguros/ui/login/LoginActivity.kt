package com.fiap.fiap_android_seguros.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.application.usecases.LoginUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.fiap_android_seguros.data.repositories.UserRepositoryImpl
import com.fiap.fiap_android_seguros.presentation.login.LoginViewModel
import com.fiap.fiap_android_seguros.presentation.login.LoginViewModelFactory
import com.fiap.fiap_android_seguros.ui.corretor.CorretorActivity
import com.fiap.fiap_android_seguros.ui.usuario.NovoCadastroActivity
import com.fiap.fiap_android_seguros.ui.usuario.UsuarioActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

//    private lateinit var loginViewModel: LoginViewModel
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            LoginViewModelFactory(
                LoginUseCase(
                    UserRepositoryImpl(
                        (UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        ))
                    )
                )
            )
        ).get(LoginViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicia escondendo o teclado
        hideKeyboard()

        startListeners()
        iniciarObserver()

    }

    private fun iniciarObserver() {
        loginViewModel.loginState.observe(this, Observer {
            when (it) {
                is RequestState.Success -> {
                    val user: UserRemoteResponse = it.data
                    // Validar se é um corretor ou um usuario normal pra chavear
 //                   startActivity((Intent(this, UsuarioActivity::class.java)))
                    startActivity((Intent(this, CorretorActivity::class.java)))

                }
                is RequestState.Error -> {
                    tvFeedbackLogin.text = it.throwable.message
                }
                is RequestState.Loading -> {

                }
                com.fiap.fiap_android_seguros.data.remote.RequestState.Loading -> TODO()
                is com.fiap.fiap_android_seguros.data.remote.RequestState.Success -> TODO()
                is com.fiap.fiap_android_seguros.data.remote.RequestState.Error -> TODO()
            }
        })

    }



    private fun startListeners() {
        btEntrar.setOnClickListener {
            loginViewModel.doLogin(
                etEmail.text.toString(),
                etSenha.text.toString()
            )
        }


        tvNovoCadastro.setOnClickListener {
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