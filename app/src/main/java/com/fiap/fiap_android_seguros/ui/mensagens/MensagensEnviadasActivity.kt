package com.fiap.fiap_android_seguros.ui.mensagens

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.application.usecases.GetUserLoggedUseCase
import com.fiap.fiap_android_seguros.application.usecases.MessageUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.presentation.mensagens.ConversasViewModel
import com.fiap.fiap_android_seguros.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.fiap_android_seguros.data.repositories.UserRepositoryImpl
import com.fiap.fiap_android_seguros.presentation.mensagens.ConversasViewModelFactory
import com.fiap.fiap_android_seguros.ui.corretor.CorretorActivity
import com.fiap.fiap_android_seguros.ui.usuario.UsuarioActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_mensagens_enviadas.*

class MensagensEnviadasActivity : AppCompatActivity() {


    private val conversasViewModel: ConversasViewModel by lazy {
        ViewModelProvider(
            this,
            ConversasViewModelFactory(
                MessageUseCase(
                    UserRepositoryImpl(
                        (UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        ))
                    ), GetUserLoggedUseCase(
                        UserRepositoryImpl(
                            (UserRemoteFirebaseDataSourceImpl(
                                FirebaseAuth.getInstance(),
                                FirebaseFirestore.getInstance()
                            ))
                        )
                    )
                )
            )
        ).get(ConversasViewModel::class.java)
    }

//    private val usuarioViewModel: UsuarioViewModel by lazy {
//        ViewModelProvider(
//            this,
//            UsuarioViewModelFactory(
//                GetUserByIdUseCase(
//                    UserRepositoryImpl(
//                        (UserRemoteFirebaseDataSourceImpl(
//                            FirebaseAuth.getInstance(),
//                            FirebaseFirestore.getInstance()
//                        ))
//                    )
//                )
//            )
//        ).get(UsuarioViewModel::class.java)
//    }


    private var origemCorretor: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        origemCorretor = intent.getStringExtra("ORIGEM_CORRETOR").equals("TRUE")
        setContentView(R.layout.activity_mensagens_enviadas)
        startListeners()
        validaCorretor()
        carregaRecyclerView()
    }

    private fun carregaRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvMensagens)
        val adapter = MensagemListaAdapter(this, origemCorretor, conversasViewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        conversasViewModel.buscaConversas()

        conversasViewModel.mensagens.observe(this, Observer { conversas ->
            conversas?.let {
                adapter.setConversas(it)
            }
        })

        conversasViewModel.removeState.observe(this, Observer {
            when (it) {
                is RequestState.Success -> {
                    adapter.removeItemDaListaDeMensagens(it.data ?: "")
                }
                RequestState.Loading -> TODO()
                is RequestState.Error -> TODO()
            }
        })
    }

    private fun validaCorretor() {
        // Valida se o login é de um corretor e adapta o front
        if (origemCorretor) {
            ivheader.setImageResource(R.drawable.listagem_corretor_header)
            linearLayoutMensagensEnviadas.setBackgroundColor(Color.rgb(97, 198, 254))
            ivVoltar2.setOnClickListener {
                startActivity(Intent(this, CorretorActivity::class.java))
                finish()
            }
        }
    }

    private fun startListeners() {

        ivVoltar2.setOnClickListener {
            startActivity(Intent(this, UsuarioActivity::class.java))
            finish()
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