package com.fiap.fiap_android_seguros.ui.mensagens

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.domain.entity.Mensagem

class MensagemListaAdapter internal constructor(context: Context) : RecyclerView.Adapter<MensagemListaAdapter.MensagemViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var mensagens = emptyList<Mensagem>()

    inner class MensagemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val mensagemItemView: TextView = itemView.findViewById(R.id.tvMensagem)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MensagemViewHolder {
        val itemView = inflater.inflate(R.layout.mensagem_item, parent, false)
        return MensagemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MensagemViewHolder, position: Int) {
        val current = mensagens[position]
        holder.mensagemItemView.text = current.remetente

    }
    internal fun setMensagens(mensagens: List<Mensagem>) {
        this.mensagens = mensagens
        notifyDataSetChanged()
    }
    override fun getItemCount() = mensagens.size
}