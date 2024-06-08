package com.example.shopmate_app.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemEntity
import com.example.shopmate_app.ui.viewmodels.ItemViewModel
import com.google.android.material.textview.MaterialTextView

class CategoryItemAdapter(private var itemList: List<ItemEntity>,
                          private var cardId: Int,
                          private var userId: Int,
                          private var selectedItemIds: Set<Int>,
                          private var itemViewModel: ItemViewModel
    )
    : RecyclerView.Adapter<CategoryItemAdapter.CardViewHolder>() {


    private val selectedPositions = mutableSetOf<Int>()
    init {
        // Inicializa los ítems seleccionados
        Log.d("selectedItemIds:", selectedItemIds.toString())
        Log.d("itemList:", itemList.toString())
        for (item in itemList) {
            if (selectedItemIds.contains(item.itemId)) {
                val position = itemList.indexOf(item)
                if (position != -1) {
                    selectedPositions.add(position)
                }
            }
        }
    }

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

        var itemCardLine = ItemCardLineEntity(
            itemCardLineId = 0, // assigned in the server
            cardId = cardId,
            createdBy = userId,
            assignedTo = userId,
            amount = 1,
            itemId = item.itemId,
            price = 0f,
            unitId = 9 // Unit-unit
        )

        holder.view.setOnClickListener {
            if (selectedPositions.contains(holder.adapterPosition)) {
                selectedPositions.remove(holder.adapterPosition)
                itemViewModel.removeItemFromACard(itemCardLine)
            } else {
                selectedPositions.add(holder.adapterPosition)
                itemViewModel.addItemToACard(itemCardLine)
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return if (selectedPositions.contains(position)) 1 else 0
    }
    fun getItem(position: Int): ItemEntity = itemList[position]
}