package com.example.shopmate_app.ui.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopmate_app.R
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CategoryEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.domain.entities.providers.CategoryProvider
import com.example.shopmate_app.ui.fragments.login.RegisterFragmentDirections
import com.example.shopmate_app.ui.fragments.profile.ProfileFragmentArgs
import com.example.shopmate_app.ui.fragments.profile.ProfileFragmentDirections
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class CategoryAdapter(private var categoryList: List<CategoryEntity>, private var cardId: Int)
    : RecyclerView.Adapter<CategoryAdapter.CardViewHolder>() {

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtCategoryName: MaterialTextView = view.findViewById<MaterialTextView>(R.id.txtCategoryName)
        val ivCategoryImage: ShapeableImageView = view.findViewById(R.id.ivCategoryImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.CardViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return CardViewHolder(layout.inflate(R.layout.card_item_category, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CardViewHolder, position: Int) {
        val category = categoryList[position]
        holder.txtCategoryName.text = category.name


        Glide.with(holder.view.context)
            .load("${AppConstants.BASE_API_URL}${category.icon}")
            .into(holder.ivCategoryImage)


        holder.view.setOnClickListener {
            CategoryProvider.selectedCategory = category
            val bundle = Bundle().apply {
                putInt("cardId", cardId)
            }
            holder.view.findNavController().navigate(R.id.action_cardDetailsViewFragment_to_cardCategorySelectItemsFragment, bundle)
        }

    }

    override fun getItemCount(): Int = categoryList.size
}