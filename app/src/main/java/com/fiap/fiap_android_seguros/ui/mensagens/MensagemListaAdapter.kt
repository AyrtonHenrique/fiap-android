package com.fiap.fiap_android_seguros.ui.mensagens

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fiap.fiap_android_seguros.R
import com.fiap.fiap_android_seguros.activity.LoginActivity
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import com.fiap.fiap_android_seguros.ui.usuario.FalarCorretorActivity
import java.util.*
import kotlin.collections.ArrayList

class MensagemListaAdapter
    internal constructor(context: Context) : RecyclerView.Adapter<MensagemListaAdapter.MensagemViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var mensagens = emptyList<Mensagem>()
    private val contexto = context;

    inner class MensagemViewHolder(itemView: View)  :
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
        val remetente = current.remetente
        val idMensagem: String = current.idMensagem

        holder.mensagemItemView.text = remetente
        val botaoEditar: ImageView = holder.itemView.findViewById(R.id.ivReply)
        val botaoApagar: ImageView = holder.itemView.findViewById(R.id.ivApagar)

        botaoEditar.setOnClickListener{
            val intent = Intent(contexto, FalarCorretorActivity::class.java)
            contexto.startActivity(intent)
        }

        botaoApagar.setOnClickListener {
            showDialog(idMensagem, remetente)
        }
        //fabNovoProduto.setOnClickListener {
        //    val intent = Intent(this@MainActivity, NovoProdutoActivity::class.java)
        //    startActivityForResult(intent, novoProdutoRequestCode)
       // }

    }

    private fun showDialog(idMensagem: String, remetente: String) {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(contexto)
        builder.setTitle("Apagar Mensagem")
        builder.setMessage("Tem certeza que gostaria de apagar a mensagem de " + remetente + "?")

        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    removeItemDaListaDeMensagens(idMensagem)
                    Toast.makeText(contexto, "Mensagem apagada!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.setPositiveButton("Sim", dialogClickListener)
        builder.setNeutralButton("Cancelar", dialogClickListener)

        dialog = builder.create()
        dialog.show()
    }

    private fun removeItemDaListaDeMensagens(idMensagem: String) {
        var lista: LinkedList<Mensagem> =  this.mensagens as LinkedList<Mensagem>
        var i: Int = 0

       lista.forEach {

            if(it.idMensagem.equals(idMensagem)) {
                lista.remove(it)
                notifyDataSetChanged()
                return
            }
            i++
        }
    }

    internal fun setMensagens(mensagens: List<Mensagem>) {
        this.mensagens = mensagens
        notifyDataSetChanged()
    }

    override fun getItemCount() = mensagens.size
}