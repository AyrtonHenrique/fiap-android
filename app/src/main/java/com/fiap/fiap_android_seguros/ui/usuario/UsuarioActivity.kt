package com.fiap.fiap_android_seguros.ui.usuario

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.application.usecases.GetUserLoggedUseCase
import com.fiap.fiap_android_seguros.application.usecases.LoginUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.fiap_android_seguros.data.repositories.UserRepositoryImpl
import com.fiap.fiap_android_seguros.presentation.login.LoginViewModel
import com.fiap.fiap_android_seguros.presentation.login.LoginViewModelFactory
import com.fiap.fiap_android_seguros.presentation.profile.ProfileViewModel
import com.fiap.fiap_android_seguros.presentation.profile.ProfileViewModelFactory
import com.fiap.fiap_android_seguros.ui.login.LoginActivity
import com.fiap.fiap_android_seguros.ui.mensagens.MensagensEnviadasActivity
import com.fiap.fiap_android_seguros.ui.sobre.SobreActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_corretor.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_usuario.*

class UsuarioActivity : AppCompatActivity() {

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            this,
            ProfileViewModelFactory(
                GetUserLoggedUseCase(
                    UserRepositoryImpl(
                        (UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        ))
                    )
                )
            )
        ).get(ProfileViewModel::class.java)
    }

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
        setContentView(R.layout.activity_usuario)
        startListeners()

        iniciarObserver()

        profileViewModel.getInfoUser()

        loginViewModel.signOutState.observe(this, Observer {
            when (it) {
                is RequestState.Success -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                RequestState.Loading -> TODO()
                is RequestState.Error -> TODO()
            }
        })


    }

    private fun iniciarObserver() {
        profileViewModel.userState.observe(this, Observer
        {
            when (it) {
                is RequestState.Success -> {
                    tvNomeUsuarioLogado.text = it.data.name
                    tvIdade.text = it.data.idade + " anos"
                }
                is RequestState.Error -> {
                    tvFeedbackLogin.text = it.throwable.message
                }

                is RequestState.Loading -> {

                }
            }
        })
    }

    private fun startListeners() {
        btSair.setOnClickListener {
            showDialog()
        }
        btSobre.setOnClickListener {
            startActivity(Intent(this, SobreActivity::class.java))
            finish()
        }

        ivFalarCorretor.setOnClickListener {
            val intent = Intent(this, FalarCorretorActivity::class.java).apply {
                putExtra("NOME_CORRETOR_LOGADO", tvNomeUsuarioLogado.text)
                putExtra("IDADE_CORRETOR_LOGADO", tvIdade.text)
            }
            startActivity(intent)
            finish()
        }
        tvFalarComUmCorretor.setOnClickListener {
            val intent = Intent(this, FalarCorretorActivity::class.java).apply {
                putExtra("NOME_CORRETOR_LOGADO", tvNomeUsuarioLogado.text)
                putExtra("IDADE_CORRETOR_LOGADO", tvIdade.text)
            }
            startActivity(intent)
            finish()
        }

        ivPesquisarPlanos.setOnClickListener {
            val intent =  Intent(this, PesquisarPlanosActivity::class.java).apply {
                putExtra("NOME_CORRETOR_LOGADO", tvNomeUsuarioLogado.text)
                putExtra("IDADE_CORRETOR_LOGADO", tvIdade.text)
            }
            startActivity(intent)
            finish()
        }
        tvPesquisarPlano.setOnClickListener {
            val intent =  Intent(this, PesquisarPlanosActivity::class.java).apply {
                putExtra("NOME_CORRETOR_LOGADO", tvNomeUsuarioLogado.text)
                putExtra("IDADE_CORRETOR_LOGADO", tvIdade.text)
            }
            startActivity(intent)
            finish()
        }

        ivGerenciarMensagens.setOnClickListener {
            val intent =   Intent(this, MensagensEnviadasActivity::class.java).apply {
                putExtra("NOME_CORRETOR_LOGADO", tvNomeUsuarioLogado.text)
                putExtra("IDADE_CORRETOR_LOGADO", tvIdade.text)
            }
            startActivity(intent)
            finish()
        }
        tvGerenciarMensagens.setOnClickListener {
            val intent =   Intent(this, MensagensEnviadasActivity::class.java).apply {
                putExtra("NOME_CORRETOR_LOGADO", tvNomeUsuarioLogado.text)
                putExtra("IDADE_CORRETOR_LOGADO", tvIdade.text)
            }
            startActivity(intent)
            finish()
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

        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    loginViewModel.signOut()


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