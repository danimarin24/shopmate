package com.example.shopmate_app.ui.adapters

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginEnd
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopmate_app.R
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.enums.CardViewTypeEnum
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.providers.CardProvider
import com.example.shopmate_app.ui.viewmodels.CardViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textview.MaterialTextView

class CardAdapter(private var cardList: List<CardEntity>,
                  private val currentUserId: String,
                  private val profileUserId: String,
                  private val isEditMode: Boolean,
                  private val isHome: Boolean,
                  private val icons: List<String> = emptyList(),
                  private var members: List<String>? = emptyList()
    )
    : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var cardSeleccionada: Int = -1

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cardBakground: ConstraintLayout = view.findViewById(R.id.cardContraintBackground)
        val txtCardTitle: MaterialTextView = view.findViewById(R.id.txtCardTitle)
        val txtNSaves: MaterialTextView = view.findViewById(R.id.txtNSaves)
        val categoryIconsContainer: LinearLayout = view.findViewById(R.id.categoryIconsContainer)
        val profileImageCreator: ShapeableImageView = view.findViewById(R.id.profileImage)
        val peopleProfilesContainer: LinearLayout = view.findViewById(R.id.peopleProfilesContainer)
        val btnAddNewMember: FloatingActionButton = view.findViewById(R.id.btnAddNewMember)
        val btnViewList: LinearLayout = view.findViewById(R.id.btnViewList)
        val btnOpenCard: ImageView = view.findViewById(R.id.btnOpenCard)
        val btnShareList: LinearLayout = view.findViewById(R.id.btnShareList)
        val btnEditList: LinearLayout = view.findViewById(R.id.btnEditList)
        val btnSaveList: LinearLayout = view.findViewById(R.id.btnSaveList)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.CardViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)

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
        val bundle = Bundle().apply {
            putBoolean("canManage", currentUserId == profileUserId)
        }
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
                holder.view.findNavController().navigate(R.id.cardDetailsViewFragment, bundle)
            }
            notifyDataSetChanged()
        }

        holder.btnViewList.setOnClickListener {
            if (cardSeleccionada == holder.adapterPosition) {
                cardSeleccionada = -1
                //changeVisibility(holder, true)
            } else {
                cardSeleccionada = holder.adapterPosition
                //changeVisibility(holder, false)
                CardProvider.selectedCard = card
                holder.view.findNavController().navigate(R.id.cardDetailsViewFragment, bundle)
            }
            notifyDataSetChanged()
        }

        holder.btnOpenCard.setOnClickListener {
            if (cardSeleccionada == holder.adapterPosition) {
                cardSeleccionada = -1
                //changeVisibility(holder, true)
            } else {
                cardSeleccionada = holder.adapterPosition
                //changeVisibility(holder, false)
                CardProvider.selectedCard = card
                holder.view.findNavController().navigate(R.id.cardDetailsViewFragment, bundle)
            }
            notifyDataSetChanged()
        }

        holder.btnShareList.setOnClickListener {
            holder.view.findNavController().navigate(R.id.shareCardFragment, bundle)

        }


        var sizeInDp = 20 // Tamaño en dp
        var sizeInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, sizeInDp.toFloat(), holder.view.resources.displayMetrics).toInt()
        if (icons.isNotEmpty()) {
            for (url in icons) {
                val imageView = ImageView(holder.view.context)
                val layoutParams = LinearLayout.LayoutParams(sizeInPx, sizeInPx)
                layoutParams.setMargins(0, 0, 8, 0)
                imageView.layoutParams = layoutParams

                Glide.with(holder.view.context)
                    .load("${AppConstants.BASE_API_URL}${url}")
                    .into(imageView)

                holder.categoryIconsContainer.addView(imageView)
            }
        }

        sizeInDp = 40 // Tamaño en dp
        sizeInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, sizeInDp.toFloat(), holder.view.resources.displayMetrics).toInt()

        members = listOf("/api/images/user/638512277351423182.jpg", "/api/images/user/default_image.jpg", "/api/images/user/638513348400864796.jpg")
        Glide.with(holder.view.context)
            .load("${AppConstants.BASE_API_URL}/api/images/user/638513043795905625.jpeg")
            .into(holder.profileImageCreator)
        if (members?.isNotEmpty() == true) {
            for (member in members!!) {
                val imageView = ShapeableImageView(holder.view.context).apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    shapeAppearanceModel = ShapeAppearanceModel.builder(
                        context,
                        0,
                        R.style.circular
                    ).build()
                }

                val layoutParams = LinearLayout.LayoutParams(sizeInPx, sizeInPx)
                layoutParams.setMargins(0, 0, -10, 0)
                imageView.layoutParams = layoutParams
                Glide.with(holder.view.context)
                    .load("${AppConstants.BASE_API_URL}${member}")
                    .into(imageView)

                holder.peopleProfilesContainer.addView(imageView)
            }
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
