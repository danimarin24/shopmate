package com.example.shopmate_app.ui.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.domain.entities.enums.CardViewTypeEnum
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.providers.CardProvider
import com.google.android.material.textview.MaterialTextView

class CardAdapter(private var cardList: List<CardEntity>,
                  private val currentUserId: String,
                  private val profileUserId: String,
                  private val isEditMode: Boolean,
                  private val isHome: Boolean)
    : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var cardSeleccionada: Int = -1

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtCardTitle: MaterialTextView = view.findViewById(R.id.txtCardTitle)
        val cardBakground: ConstraintLayout = view.findViewById(R.id.cardContraintBackground)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.CardViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        //val layout = R.layout.card_item_rcv


        val layout = when (CardViewTypeEnum.entries[viewType]) {
            CardViewTypeEnum.HOME -> R.layout.card_item_view_home
            CardViewTypeEnum.HOME_EDIT -> R.layout.card_item_view_home_edit
            CardViewTypeEnum.MY_PROFILE -> R.layout.card_item_view_profile_edit
            CardViewTypeEnum.OTHER_PROFILE_PUBLIC -> R.layout.card_item_view_profile
            CardViewTypeEnum.OTHER_PROFILE_PUBLIC_TEMPLATE -> R.layout.card_item_view_profile_template
        }

        return CardViewHolder(layoutInflater.inflate(layout, parent, false))

    }

    override fun onBindViewHolder(holder: CardAdapter.CardViewHolder, position: Int) {
        val card = cardList[position]
        holder.txtCardTitle.text = card.cardName
        val colorHex = card.color.colorHex
        try {
            if (colorHex != null && colorHex.isNotEmpty()) {
                holder.cardBakground.setBackgroundColor(Color.parseColor("#$colorHex"))
            } else {
                throw IllegalArgumentException("Invalid color hex value")
            }
        } catch (e: Exception) {
            holder.cardBakground.setBackgroundResource(R.color.md_theme_secondaryContainer)
        }
        holder.view.setOnClickListener {
            if (cardSeleccionada == holder.adapterPosition) {
                cardSeleccionada = -1
                //changeVisibility(holder, true)
            } else {
                cardSeleccionada = holder.adapterPosition
                //changeVisibility(holder, false)
                CardProvider.selectedCard = card
                holder.view.findNavController().navigate(R.id.action_profileFragment_to_cardDetailsViewFragment)
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = filteredCardList().size

    override fun getItemViewType(position: Int): Int {
        val card = cardList[position]
        return when {
            currentUserId == profileUserId && isHome -> {
                if (isEditMode) {
                    CardViewTypeEnum.HOME_EDIT.ordinal
                } else {
                    CardViewTypeEnum.HOME.ordinal
                }
            }
            card.ownerId == profileUserId.toInt() && !card.isPublic.toBoolean() -> {
                CardViewTypeEnum.MY_PROFILE.ordinal
            }
            card.isPublic.toBoolean() && !card.isTemplate.toBoolean() -> {
                CardViewTypeEnum.OTHER_PROFILE_PUBLIC.ordinal
            }
            card.isPublic.toBoolean() && card.isTemplate.toBoolean() -> {
                CardViewTypeEnum.OTHER_PROFILE_PUBLIC_TEMPLATE.ordinal
            }
            else -> CardViewTypeEnum.HOME.ordinal // Default case
        }
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
