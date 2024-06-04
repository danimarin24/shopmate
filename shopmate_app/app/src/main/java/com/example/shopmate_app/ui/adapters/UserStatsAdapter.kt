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
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class UserStatsAdapter(private var userList: List<UserEntity>)
    : RecyclerView.Adapter<UserStatsAdapter.CardViewHolder>() {

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtUserUsername: MaterialTextView = view.findViewById(R.id.txtUserUsername)
        val txtUserName: MaterialTextView = view.findViewById(R.id.txtUserName)
        val ivUserProfileImage: ShapeableImageView = view.findViewById(R.id.ivUserProfileImage)
        val btnAction: MaterialButton = view.findViewById(R.id.btnAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserStatsAdapter.CardViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return CardViewHolder(layout.inflate(R.layout.card_item_user, parent, false))
    }

    override fun onBindViewHolder(holder: UserStatsAdapter.CardViewHolder, position: Int) {
        val user = userList[position]
        holder.txtUserUsername.text = user.username
        holder.txtUserName.text = user.name

        if (user.googleToken.isNullOrEmpty()) {
            Glide.with(holder.view.context)
                .load("${AppConstants.BASE_API_URL}${user.profileImage}")
                .into(holder.ivUserProfileImage)
        } else {
            Glide.with(holder.view.context)
                .load(user.profileImage)
                .into(holder.ivUserProfileImage)
        }

        holder.btnAction.setOnClickListener {
            // follow or unfollow
        }

        holder.view.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("profileId", user.userId!!)
            }
            holder.view.findNavController().navigate(R.id.profileFragment, bundle)
        }

    }

    override fun getItemCount(): Int = userList.size
}