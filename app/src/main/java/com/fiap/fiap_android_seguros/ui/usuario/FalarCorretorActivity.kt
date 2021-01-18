package com.fiap.fiap_android_seguros.ui.usuario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.application.usecases.GetUserLoggedUseCase
import com.fiap.fiap_android_seguros.application.usecases.MessageUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.fiap_android_seguros.data.repositories.UserRepositoryImpl
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import com.fiap.fiap_android_seguros.presentation.mensagens.MensagemViewModel
import com.fiap.fiap_android_seguros.presentation.mensagens.MensagemViewModelFactory
import com.fiap.fiap_android_seguros.ui.mensagens.MensagensEnviadasActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_falar_cliente.*
import kotlinx.android.synthetic.main.activity_falar_corretor.*
import kotlinx.android.synthetic.main.activity_falar_corretor.btEnviarMensagem
import kotlinx.android.synthetic.main.activity_login.*
import java.util.ArrayList

class FalarCorretorActivity : AppCompatActivity() {

    private val messageViewModel: MensagemViewModel by lazy {
        ViewModelProvider(
            this,
            MensagemViewModelFactory(
                MessageUseCase(
                    UserRepositoryImpl(
                        (UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        ))
                    ),
                    GetUserLoggedUseCase(
                        UserRepositoryImpl(
                            (UserRemoteFirebaseDataSourceImpl(
                                FirebaseAuth.getInstance(),
                                FirebaseFirestore.getInstance()
                            ))
                        )
                    )
                )
            )
        ).get(MensagemViewModel::class.java)
    }

    private var mensagens: ArrayList<Mensagem>? = ArrayList<Mensagem>()
    private var origemCorretor: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_falar_corretor)
        startListeners()
        mensagens = intent.extras?.getParcelableArrayList<Mensagem>("mensagens")
        origemCorretor = intent.getStringExtra("ORIGEM_CORRETOR")
        recuperaDadosMensagens()

        validaCorretor()
        iniciarObserver()

    }

    private fun iniciarObserver() {
        messageViewModel.messageState.observe(this, Observer {
            when (it) {
                is RequestState.Success -> {
                    tvMensagemParaEnviar.text.clear()
                    AdicionarNovaMensagem(it.data)
                    if (intent.getStringExtra("ID_CONVERSA")?.isNotEmpty() != true) {
                        val intent = Intent(this, MensagensEnviadasActivity::class.java).apply {
                            //putExtra("ORIGEM_CORRETOR", "TRUE")
                        }
                        startActivity(intent)
                        finish()
                    }
                }
                is RequestState.Error -> {
                    tvFeedbackLogin.text = it.throwable.message
                }
                is RequestState.Loading -> {

                }

            }
        })
    }

    private fun validaCorretor() {
        // Valida se o login é de um corretor e adapta o front

        if (origemCorretor.equals("TRUE")) {
            ivHeaderEnviarMensagens.setImageResource(R.drawable.listagem_corretor_header)
            tvFalarCom.text = "Falar com o Cliente"
            textView19.text = intent.getStringExtra("NOME_CORRETOR_LOGADO")
            textView20.text = intent.getStringExtra("IDADE_CORRETOR_LOGADO") + " anos"
            ivVoltar4.setOnClickListener {
                val intent = Intent(this, MensagensEnviadasActivity::class.java).apply {
                    putExtra("ORIGEM_CORRETOR", "TRUE")
                }
                startActivity(intent)
                finish()
            }
        }

    }

    private fun recuperaDadosMensagens() {
//        val ehcorretor = intent.getStringExtra("ORIGEM_CORRETOR")

        tvMensagemEnviada.text = ""
        var mensagensHtml = ""

        mensagens?.forEach { mensagensHtml += geraHtmlMensagem(origemCorretor, it) }

        tvMensagemEnviada
            .append(
                HtmlCompat.fromHtml(
                    mensagensHtml,
                    FROM_HTML_MODE_LEGACY
                )
            )
        tvMensagemEnviada.movementMethod = ScrollingMovementMethod()
    }

    private fun AdicionarNovaMensagem(msg: Mensagem) {
        tvMensagemEnviada
            .append(
                HtmlCompat.fromHtml(
                    geraHtmlMensagem(origemCorretor, msg),
                    FROM_HTML_MODE_LEGACY
                )
            )
    }

    private fun geraHtmlMensagem(
        ehcorretor: String?,
        it: Mensagem
    ): String {
        var mensagensHtml1 = ""
        if (ehcorretor == "TRUE") {
            if (it.enviadoPeloCorretor) {
                mensagensHtml1 += "<br/><div style=\"text-align: end !important;\">" + it.textoMensagem + "</div>"
            } else {
                mensagensHtml1 += "<br/><span style=\"text-align:left\">" + it.textoMensagem + "</span>"
            }
        } else {
            if (!it.enviadoPeloCorretor) {
                mensagensHtml1 += "<br/><div style=\"text-align: end !important;\">" + it.textoMensagem + "</div>"
            } else {
                mensagensHtml1 += "<br/><span style=\"text-align:left\">" + it.textoMensagem + "</span>"

            }
        }
        return mensagensHtml1
    }

    private fun startListeners() {
        ivVoltar4.setOnClickListener {
            startActivity(Intent(this, UsuarioActivity::class.java))
            finish()
        }
        btEnviarMensagem.setOnClickListener {
            val idconversa = intent.getStringExtra("ID_CONVERSA")
            if (idconversa.isNullOrEmpty()) {
                messageViewModel.sendMessageUser(
                    tvMensagemParaEnviar.text.toString(),
                    intent.getStringExtra("id_cliente") ?: ""
                )

            } else {
                messageViewModel.send(
                    tvMensagemParaEnviar.text.toString(),
                    intent.getStringExtra("ID_CONVERSA") ?: ""
                )
            }
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