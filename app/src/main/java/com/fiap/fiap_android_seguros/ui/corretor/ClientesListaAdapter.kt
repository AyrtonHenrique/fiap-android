package com.fiap.fiap_android_seguros.ui.corretor

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
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import com.fiap.fiap_android_seguros.domain.entity.User
import com.fiap.fiap_android_seguros.ui.usuario.FalarCorretorActivity
import kotlinx.android.synthetic.main.activity_falar_corretor.*
import kotlinx.android.synthetic.main.activity_meus_clientes.*
import kotlinx.android.synthetic.main.activity_pesquisar_clientes.*
import java.util.*

class ClientesListaAdapter
internal constructor(context: Context) :
    RecyclerView.Adapter<ClientesListaAdapter.ClienteViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var clientes = emptyList<User>()
    private val contexto = context;


    inner class ClienteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val clienteItemView: TextView = itemView.findViewById(R.id.tvClienteItem)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ClienteViewHolder {
        val itemView = inflater.inflate(R.layout.clientes_item, parent, false)
        return ClienteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val current = clientes[position]
        val cliente = current.name

        holder.clienteItemView.text = cliente
        val botaoResponder: ImageView = holder.itemView.findViewById(R.id.imageView3)

        botaoResponder.setOnClickListener {
            val intent = Intent(contexto, FalarCorretorActivity::class.java).apply {
                putExtra("id_cliente", current.id)
                putExtra("ORIGEM_CORRETOR","TRUE")
            }
            contexto.startActivity(intent)
        }


    }

    internal fun setClientes(clientes: List<User>) {
        this.clientes = clientes
        notifyDataSetChanged()
    }

    override fun getItemCount() = clientes.size
}