package com.example.shopmate_app.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.google.android.material.textview.MaterialTextView

class CardAdapter(private var cardList: List<CardEntity>)
    : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var cardSeleccionada: Int = -1

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtCardTitle: MaterialTextView = view.findViewById(R.id.txtCardTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.CardViewHolder {
        var layout = LayoutInflater.from(parent.context)
        return CardViewHolder(layout.inflate(R.layout.card_item_rcv, parent, false))

        /*
        return if (viewType == 0)
            CardViewHolder(layout.inflate(R.layout.card_normal, parent, false))
        else
            CardViewHolder(layout.inflate(R.layout.card_edit, parent, false))
         */
    }

    override fun onBindViewHolder(holder: CardAdapter.CardViewHolder, position: Int) {
        val card = cardList[position]
        holder.txtCardTitle.text = "car title todo"
        //holder.txtCardTitle.text = card.title
        //todo colorId
        //holder.colorCardBackground.setBackgroundColor(Color.parseColor("#${cardList?.get(position)!!.colorHex}"))
        //holder.txtColorName.text = cardList?.get(position)!!.name

        holder.view.setOnClickListener {
            if (cardSeleccionada == holder.adapterPosition) {
                cardSeleccionada = -1
                //changeVisibility(holder, true)
            } else {
                cardSeleccionada = holder.adapterPosition
                //changeVisibility(holder, false)
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = cardList.size

    override fun getItemViewType(position: Int): Int {
        return if (position == cardSeleccionada) 1 else 0
    }
}