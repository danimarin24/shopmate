package com.example.shopmate_app.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.domain.entities.newtworkEntities.ColorEntity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class ColorsChoseAdapter(var colorList: List<ColorEntity>?)
    : RecyclerView.Adapter<ColorsChoseAdapter.CardViewHolder>() {

    private var cardSeleccionada: Int = -1

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val colorCardBackground = view.findViewById<ConstraintLayout>(R.id.cardBackground)
        val txtColorName = view.findViewById<MaterialTextView>(R.id.txtColorName)
        val ivSelectedIcon = view.findViewById<ImageView>(R.id.ivSelectedIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsChoseAdapter.CardViewHolder {
        var layout = LayoutInflater.from(parent.context)

        return if (viewType == 0)
            CardViewHolder(layout.inflate(R.layout.color_card_selection, parent, false))
        else
            CardViewHolder(layout.inflate(R.layout.color_card_selection_selected, parent, false))
    }

    override fun onBindViewHolder(holder: ColorsChoseAdapter.CardViewHolder, position: Int) {
        val colorHex = colorList?.get(position)!!.colorHex
        try {
            if (colorHex != null && colorHex.isNotEmpty()) {
                holder.colorCardBackground.setBackgroundColor(Color.parseColor("#${colorHex}"))
            } else {
                throw IllegalArgumentException("Invalid color hex value")
            }
        } catch (e: Exception) {
            holder.colorCardBackground.setBackgroundResource(R.color.md_theme_secondaryContainer)
        }
        holder.txtColorName.text = colorList?.get(position)!!.name

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

    override fun getItemCount(): Int = colorList!!.size

    override fun getItemViewType(position: Int): Int {
        return if (position == cardSeleccionada) 1 else 0
    }

     fun getSelectedColor(): ColorEntity? {
        return colorList?.get(cardSeleccionada)
    }
}