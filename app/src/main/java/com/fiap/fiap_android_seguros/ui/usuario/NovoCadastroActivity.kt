package com.fiap.fiap_android_seguros.ui.usuario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.ui.login.LoginActivity
import com.fiap.fiap_android_seguros.application.usecases.CreateUserUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.fiap_android_seguros.data.repositories.UserRepositoryImpl
import com.fiap.fiap_android_seguros.presentation.newUser.NewUserViewModel
import com.fiap.fiap_android_seguros.presentation.newUser.NewUserViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_novo_cadastro.*

class NovoCadastroActivity : AppCompatActivity() {

    private val newUserViewModel: NewUserViewModel by lazy {
        ViewModelProvider(
            this,
            NewUserViewModelFactory(
                CreateUserUseCase(
                    UserRepositoryImpl(
                        (UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        ))
                    )
                )
            )
        ).get(NewUserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_cadastro)
        iniciarObserver()
        startListeners()

    }

    private fun iniciarObserver() {
        newUserViewModel.registerState.observe(this, Observer {
            when (it) {
                is RequestState.Success -> {
                    startActivity((Intent(this, LoginActivity::class.java)))
                    finish()
                }
                is RequestState.Error -> {
                    registerInfo.text = it.throwable.message
                }
                is RequestState.Loading -> {

                }
            }
        })

    }

    private fun startListeners() {
        ivBack.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btCadastrar.setOnClickListener{
            newUserViewModel.signOn(
                nome = etNome.text.toString(),
                address = etEndereco.text.toString(),
                idade = etIdade.text.toString(),
                corretor = swCorretor.isChecked,
                email = etNomeUsuario.text.toString(),
                password = etPassword.text.toString()
            )
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