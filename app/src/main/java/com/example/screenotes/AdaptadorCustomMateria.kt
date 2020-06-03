package com.example.screenotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorCustomMateria(items: ArrayList<DataMat>, var listener: ClickListenerMat) : RecyclerView.Adapter<AdaptadorCustomMateria.ViewHolder>() {

    var items: ArrayList<DataMat> ?= null

    init {
        this.items = items
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdaptadorCustomMateria.ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.template_materias, parent, false)
        val viewHolder = ViewHolder(vista, listener)
        return  viewHolder
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    override fun onBindViewHolder(holder: AdaptadorCustomMateria.ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.id_materia?.text = item?.id_materia
        holder.nom_materia?.text = item?.nom_materia
    }

    class ViewHolder(view: View, listener: ClickListenerMat) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var view = view
        var id_materia:TextView ?= null
        var nom_materia:TextView ?= null
        var listener: ClickListenerMat ?= null

        init {
            id_materia = view.findViewById(R.id.num_materia)
            nom_materia = view.findViewById(R.id.nom_materia)
            this.listener = listener
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }
}