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
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.ui.fragments.login.RegisterFragmentDirections
import com.example.shopmate_app.ui.fragments.profile.ProfileFragmentArgs
import com.example.shopmate_app.ui.fragments.profile.ProfileFragmentDirections
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class UserAdapter(private var userList: List<UserEntity>)
    : RecyclerView.Adapter<UserAdapter.CardViewHolder>() {

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtUserUsername: MaterialTextView = view.findViewById<MaterialTextView>(R.id.txtUserUsername)
        val ivUserProfileImage: ShapeableImageView = view.findViewById(R.id.ivUserProfileImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.CardViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return CardViewHolder(layout.inflate(R.layout.card_item_user, parent, false))
    }

    override fun onBindViewHolder(holder: UserAdapter.CardViewHolder, position: Int) {
        val user = userList[position]
        holder.txtUserUsername.text = user.username

        if (user.googleToken.isNullOrEmpty()) {
            Glide.with(holder.view.context)
                .load("${AppConstants.BASE_API_URL}${user.profileImage}")
                .into(holder.ivUserProfileImage)
        } else {
            Glide.with(holder.view.context)
                .load(user.profileImage)
                .into(holder.ivUserProfileImage)
        }

        holder.view.setOnClickListener {
            Log.e("userid", user.userId.toString())
            val bundle = Bundle().apply {
                putInt("profileId", user.userId!!)
            }
            holder.view.findNavController().navigate(R.id.profileFragment, bundle)
        }

    }

    override fun getItemCount(): Int = userList.size
}