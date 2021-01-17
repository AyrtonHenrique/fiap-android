package com.fiap.fiap_android_seguros.ui.mensagens

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.domain.entity.Conversa
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import com.fiap.fiap_android_seguros.presentation.mensagens.ConversasViewModel
import com.fiap.fiap_android_seguros.ui.usuario.FalarCorretorActivity
import kotlin.collections.ArrayList

class MensagemListaAdapter
internal constructor(
    context: Context,
    origem: Boolean,
    private val conversasViewModel: ConversasViewModel
) : RecyclerView.Adapter<MensagemListaAdapter.MensagemViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var conversas = emptyList<Conversa>()
    private val contexto = context;
    private val origemCorretor = origem

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
        val current = conversas[position]
        val idConversa: String = current.id
        val remetente = if (origemCorretor) current.nomeCliente else current.nomeCorretor

        holder.mensagemItemView.text = remetente

        val botaoResponder: ImageView = holder.itemView.findViewById(R.id.ivReply)
        val botaoApagar: ImageView = holder.itemView.findViewById(R.id.ivApagar)

        botaoResponder.setOnClickListener {
            val bundle = Bundle()
            val mensagens = ArrayList<Mensagem>()

            mensagens.addAll(current.mensagagens)

            bundle.putParcelableArrayList("mensagens", mensagens)

            val intent = Intent(contexto, FalarCorretorActivity::class.java).apply {
                //putExtra("MENSAGEM", mensagem)
                putExtras(bundle)
                putExtra("REMETENTE", remetente)
                putExtra("ID_CONVERSA", idConversa)
//                putExtra("EMAIL_REMETENTE", emailRemetente)
                if (origemCorretor)
                    putExtra("ORIGEM_CORRETOR", "TRUE")
            }
            contexto.startActivity(intent)
        }

        botaoApagar.setOnClickListener {
            showDialog(idConversa, remetente)
        }
    }

    private fun showDialog(idConversa: String, remetente: String) {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(contexto)
        builder.setTitle("Apagar Mensagem")
        builder.setMessage("Tem certeza que gostaria de apagar a mensagem de " + remetente + "?")


        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
//                    removeItemDaListaDeMensagens(idConversa)
                    Toast.makeText(contexto, "Mensagem apagada!!!", Toast.LENGTH_SHORT).show()
                    conversasViewModel.remover(idConversa)
                }
            }
        }
        builder.setPositiveButton("Sim", dialogClickListener)
        builder.setNeutralButton("Cancelar", dialogClickListener)

        dialog = builder.create()
        dialog.show()
    }

    fun removeItemDaListaDeMensagens(idConversa: String) {
        var lista = this.conversas.toMutableList()
        lista.forEach {
            if (it.id.equals(idConversa)) {
                lista.remove(it)

                setConversas(lista)

                return
            }
        }
    }

    internal fun setConversas(mensagens: List<Conversa>) {
        this.conversas = mensagens
        notifyDataSetChanged()
    }

    override fun getItemCount() = conversas.size
}