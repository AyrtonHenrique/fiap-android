package com.fiap.fiap_android_seguros.ui.corretor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fiap.fiap_android_seguros.R
import kotlinx.android.synthetic.main.activity_falar_cliente.*

class FalarClienteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_falar_cliente)

        tvNome.text = intent.getStringExtra("NOME_CORRETOR_LOGADO")
        tvIdade.text = intent.getStringExtra("IDADE_CORRETOR_LOGADO") + " anos"
    }
}