package com.example.shopmate_app.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.domain.entities.enums.CardViewTypeEnum
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.google.android.material.textview.MaterialTextView

class CardAdapter(private var cardList: List<CardEntity>,
                  private val currentUserId: String,
                  private val profileUserId: String)
    : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var cardSeleccionada: Int = -1

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        //val txtCardTitle: MaterialTextView = view.findViewById(R.id.txtCardTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.CardViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        val layout = R.layout.card_item_rcv

        /*
        TODO: ('Create all layouts cards')
        val layout = when (viewType) {
            CardViewTypeEnum.MY_PROFILE -> R.layout.card_my_profile
            CardViewTypeEnum.OTHER_PROFILE_PUBLIC -> R.layout.card_other_profile_public
            CardViewTypeEnum.OTHER_PROFILE_PUBLIC_TEMPLATE -> R.layout.card_other_profile_template
            CardViewTypeEnum.HOME -> R.layout.card_home
            CardViewTypeEnum.HOME_EDIT -> R.layout.card_home_edit
        }
         */
        return CardViewHolder(layoutInflater.inflate(layout, parent, false))

    }

    override fun onBindViewHolder(holder: CardAdapter.CardViewHolder, position: Int) {
        val card = cardList[position]

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

    override fun getItemCount(): Int = filteredCardList().size

    override fun getItemViewType(position: Int): Int {
        return if (position == cardSeleccionada) 1 else 0
    }

    private fun filteredCardList(): List<CardEntity> {
        return if (currentUserId == profileUserId) {
            cardList
        } else {
            cardList.filter { it.isPublic.toBoolean() }
        }
    }
}

private fun Int.toBoolean(): Boolean {
    return this != 0
}
