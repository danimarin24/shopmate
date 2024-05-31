package com.example.shopmate_app.ui.adapters

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopmate_app.R
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemEntity
import com.example.shopmate_app.domain.entities.providers.CardProvider
import com.example.shopmate_app.domain.entities.providers.ItemProvider
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class CategoryItemAdapter(private var itemList: List<ItemEntity>, private var cardId: Int)
    : RecyclerView.Adapter<CategoryItemAdapter.CardViewHolder>() {

    private var cardSeleccionada: Int = -1

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtItemName: MaterialTextView = view.findViewById(R.id.txtItemName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemAdapter.CardViewHolder {
        val layout = LayoutInflater.from(parent.context)

        return if (viewType == 0)
            CardViewHolder(layout.inflate(R.layout.card_categoryitem_default, parent, false))
        else
            CardViewHolder(layout.inflate(R.layout.card_categoryitem_selected, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryItemAdapter.CardViewHolder, position: Int) {
        val item = itemList[position]
        holder.txtItemName.text = item.name

        holder.view.setOnClickListener {
            if (cardSeleccionada == holder.adapterPosition) {
                cardSeleccionada = -1
                // eliminarla de la card
            } else {
                cardSeleccionada = holder.adapterPosition
                // añadirla a la card
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return if (position == cardSeleccionada) 1 else 0
    }

    fun getItem(position: Int): ItemEntity = itemList[position]
}