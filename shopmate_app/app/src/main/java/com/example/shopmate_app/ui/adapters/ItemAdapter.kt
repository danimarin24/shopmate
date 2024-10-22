package com.example.shopmate_app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopmate_app.R
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.example.shopmate_app.domain.entities.providers.ItemProvider
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class ItemAdapter(private var itemList: List<ItemCardLineEntity> = listOf())
    : RecyclerView.Adapter<ItemAdapter.CardViewHolder>() {

    private var cardSeleccionada: Int = -1

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtItemQuantity: MaterialTextView = view.findViewById(R.id.txtItemQuantity)
        val txtItemName: MaterialTextView = view.findViewById(R.id.txtItemName)
        val txtItemPrice: MaterialTextView = view.findViewById(R.id.txtItemPrice)
        val ivAssignedTo: ShapeableImageView = view.findViewById(R.id.ivAssignedTo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.CardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layout = R.layout.item_item_rcv
        return CardViewHolder(layoutInflater.inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: ItemAdapter.CardViewHolder, position: Int) {
        val item = itemList[position]
        holder.txtItemQuantity.text = "${item.amount} ${item.unit?.prefix}"
        holder.txtItemName.text = item.item?.name
        holder.txtItemPrice.text = "${item.price}€"

        if (item.assignedToUser?.profileImage == null) {
            Glide.with(holder.view.context)
                .load("${AppConstants.BASE_API_URL}api/images/default_image.jpg")
                .into(holder.ivAssignedTo)
        } else {
            if (item.assignedToUser.googleToken.isNullOrEmpty()) {
                Glide.with(holder.view.context)
                    .load("${AppConstants.BASE_API_URL}${item.assignedToUser.profileImage}")
                    .into(holder.ivAssignedTo)
            } else {
                Glide.with(holder.view.context)
                    .load(item.assignedToUser.profileImage)
                    .into(holder.ivAssignedTo)
            }
        }

        holder.view.setOnClickListener {
            if (cardSeleccionada == holder.adapterPosition) {
                cardSeleccionada = -1
            } else {
                cardSeleccionada = holder.adapterPosition
                ItemProvider.selectedItem = item
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun getItem(position: Int): ItemCardLineEntity = itemList[position]

    fun submitList(newItemList: List<ItemCardLineEntity>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    fun getCurrentList(): List<ItemCardLineEntity> {
        return itemList
    }
}
