package com.example.shopmate_app.ui.fragments.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.shopmate_app.R
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.databinding.FragmentLoginBinding
import com.example.shopmate_app.databinding.FragmentProfileBinding
import com.example.shopmate_app.ui.adapters.BoardAdapter
import com.example.shopmate_app.ui.adapters.CardAdapter
import com.example.shopmate_app.ui.fragments.login.RegisterProfileFragmentArgs
import com.example.shopmate_app.ui.viewmodels.BoardViewModel
import com.example.shopmate_app.ui.viewmodels.CardViewModel
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.ProfileViewModel
import com.example.shopmate_app.utils.PasswordUtils

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val boardViewModel: BoardViewModel by viewModels()
    private val cardViewModel: CardViewModel by viewModels()

    private var profileId : Int = 0

    private lateinit var currentUserId : String
    private lateinit var profileUserId : String

    private lateinit var context : Context


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        context = requireContext()

        arguments?.let {
            profileId = it.getInt("profileId", 0)
        }

        if (profileId != 0) {
            currentUserId = mainViewModel.getUserId().toString()
            profileUserId = profileId.toString()
            cardViewModel.fetchAllCards(profileId)
            profileViewModel.getUserStats(profileId)
            profileViewModel.getUserInformation(profileId)
        } else {
            currentUserId = mainViewModel.getUserId().toString()
            profileUserId = mainViewModel.getUserId().toString()
            cardViewModel.fetchAllCards(mainViewModel.getUserId()!!)
            profileViewModel.getUserStats(mainViewModel.getUserId()!!)
            profileViewModel.getUserInformation(mainViewModel.getUserId()!!)
        }



        setUpCorrectButtonsView()
        setupObservers()
        setUpListeners()

        return binding.root
    }

    private fun setupObservers() {
        profileViewModel.userStatsEntity.observe(viewLifecycleOwner, Observer {userStat ->
            if (userStat == null) {
                //err no existe ningun userStat para el id indicado
                Toast.makeText(context, "No existe ningun userStat para el id indicado", Toast.LENGTH_SHORT).show()
                return@Observer
            }
            binding.txtUserFollows.text = userStat.nfollows.toString()
            binding.txtUserFollowers.text = userStat.nfollowers.toString()
            binding.txtUserTotalSaves.text = userStat.nsaves.toString()
        })

        profileViewModel.userEntity.observe(viewLifecycleOwner, Observer {user ->
            if (user == null) {
                //err no existe ningun userStat para el id indicado
                Log.e("ERROR", "ES NULL")
                Toast.makeText(context, "No existe ningun user para el id indicado", Toast.LENGTH_SHORT).show()
                return@Observer
            }
            var usernameWithAt = "@${user.username}"
            binding.txtUserUsername.text = usernameWithAt

            if (user.googleToken.isNullOrEmpty()) {
                Glide.with(findNavController().context)
                    .load("${AppConstants.BASE_API_URL}${user.profileImage}")
                    .into(binding.profileImage)
            } else {
                Glide.with(findNavController().context)
                    .load(user.profileImage)
                    .into(binding.profileImage)
            }
        })

        cardViewModel.cards.observe(viewLifecycleOwner) { cards ->
            if (cards.isNullOrEmpty()) {
                binding.rcvProfileCards.showEmptyView()
            } else {
                binding.rcvProfileCards.hideAllViews()
                binding.rcvProfileCards.recyclerView.adapter = CardAdapter(cards, currentUserId, profileUserId, isEditMode = false, isHome = false)
                binding.rcvProfileCards.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }

        cardViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.rcvProfileCards.showLoadingView()
            }
        }

        cardViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                binding.rcvProfileCards.showErrorView()
            }
        }
    }

    private fun setUpCorrectButtonsView() {
        binding.btnEditProfile.visibility = if (profileUserId == currentUserId) View.VISIBLE else View.GONE
        binding.btnFollowUnfollow.visibility = if (profileUserId == currentUserId) View.GONE else View.VISIBLE

        var isFollowing = true
        // controll if im following or i need to follow
        if (isFollowing) {
            binding.btnFollowUnfollow.text = "Siguiendo"
            binding.btnFollowUnfollow.setBackgroundColor(resources.getColor(R.color.md_theme_primaryContainer))
        } else {
            binding.btnFollowUnfollow.text = "Seguir"
            binding.btnFollowUnfollow.setBackgroundColor(resources.getColor(R.color.md_theme_errorContainer_mediumContrast))
        }
    }

    private fun setUpListeners() {
        binding.btnFollowUnfollow.setOnClickListener {
            // follow

            // or unfollow depends
        }

        binding.btnEditProfile.setOnClickListener {
            // navigate to edit profile

        }

        binding.btnShareProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_shareProfileFragment)
        }
    }
}