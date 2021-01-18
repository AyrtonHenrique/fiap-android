package com.fiap.fiap_android_seguros.ui.corretor

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.application.usecases.GetUserLoggedUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.fiap_android_seguros.data.repositories.UserRepositoryImpl
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
import androidx.lifecycle.Observer
import com.fiap.fiap_android_seguros.application.usecases.LoginUseCase
import com.fiap.fiap_android_seguros.presentation.login.LoginViewModel
import com.fiap.fiap_android_seguros.presentation.login.LoginViewModelFactory

class CorretorActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_corretor)
        startListeners()
        profileViewModel.getInfoUser()
        iniciarObserver()

    }

    private fun iniciarObserver() {
        profileViewModel.userState.observe(this, Observer
        {
            when (it) {
                is RequestState.Success -> {
                    tvNomeCorretor.text = it.data.name
                    textView8.text = it.data.idade + " anos"
                }
                is RequestState.Error -> {
                    tvFeedbackLogin.text = it.throwable.message
                }

                is RequestState.Loading -> {

                }
            }
        })

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

    private fun startListeners() {
        btSairCorretor.setOnClickListener {
            showDialog()
        }

        btSobreCorretor.setOnClickListener {
            val intent = Intent(this, SobreActivity::class.java).apply {
                putExtra("ORIGEM_CORRETOR", "TRUE")
            }
            startActivity(intent)
            finish()
        }

        ivGerenciarMensagensCorretor.setOnClickListener {
            val intent = Intent(this, MensagensEnviadasActivity::class.java).apply {
                putExtra("ORIGEM_CORRETOR", "TRUE")
                putExtra("NOME_CORRETOR_LOGADO", tvNomeCorretor.text.toString())
                putExtra("IDADE_CORRETOR_LOGADO", textView8.text.toString())
            }
            startActivity(intent)
            finish()
        }

        tvGerenciarMensagensCorretor.setOnClickListener {
            val intent = Intent(this, MensagensEnviadasActivity::class.java).apply {
                putExtra("ORIGEM_CORRETOR", "TRUE")
                putExtra("NOME_CORRETOR_LOGADO", tvNomeCorretor.text.toString())
                putExtra("IDADE_CORRETOR_LOGADO", textView8.text.toString())
            }
            startActivity(intent)
            finish()
        }

        ivPesquisarClientes.setOnClickListener {
            val intent = Intent(this, PesquisarClientesActivity::class.java).apply {
                putExtra("ORIGEM_CORRETOR", "TRUE")
                putExtra("NOME_CORRETOR_LOGADO", tvNomeCorretor.text.toString())
                putExtra("IDADE_CORRETOR_LOGADO", textView8.text.toString())
            }
            startActivity(intent)
            finish()
        }

        tvPesquisarClientes.setOnClickListener {
            val intent = Intent(this, PesquisarClientesActivity::class.java).apply {
                putExtra("ORIGEM_CORRETOR", "TRUE")
                putExtra("NOME_CORRETOR_LOGADO", tvNomeCorretor.text.toString())
                putExtra("IDADE_CORRETOR_LOGADO", textView8.text.toString())
            }
            startActivity(intent)
            finish()
        }

        ivMeusClientes.setOnClickListener {
            val intent = Intent(this, MeusClientesActivity::class.java).apply {
                putExtra("ORIGEM_CORRETOR", "TRUE")
                putExtra("NOME_CORRETOR_LOGADO", tvNomeCorretor.text.toString())
                putExtra("IDADE_CORRETOR_LOGADO", textView8.text.toString())
            }
            startActivity(intent)
            finish()
        }

        tvMeusClientes.setOnClickListener {
            val intent = Intent(this, MeusClientesActivity::class.java).apply {
                putExtra("ORIGEM_CORRETOR", "TRUE")
                putExtra("NOME_CORRETOR_LOGADO", tvNomeCorretor.text.toString())
                putExtra("IDADE_CORRETOR_LOGADO", textView8.text.toString())
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