package com.example.shopmate_app.ui.fragments.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.shopmate_app.R
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.databinding.FragmentProfileBinding
import com.example.shopmate_app.domain.entities.providers.UserProvider
import com.example.shopmate_app.ui.adapters.CardAdapter
import com.example.shopmate_app.ui.viewmodels.CardViewModel
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.ProfileViewModel
import com.example.shopmate_app.ui.viewmodels.UserViewModel
import com.google.android.material.snackbar.Snackbar

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
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

        userViewModel.fetchIfIsFollowing(currentUserId.toInt(), profileUserId.toInt())

        setUpCorrectButtonsView()
        setupObservers()
        setUpListeners()


        return binding.root
    }

    private fun setupObservers() {
        userViewModel.isFollowing.observe(viewLifecycleOwner) {
            profileViewModel.getUserStats(profileId)
            if (it) {
                binding.btnFollowUnfollow.text = "Siguiendo"
                binding.btnFollowUnfollow.setBackgroundResource(R.drawable.custom_input_rounded)
            } else {
                binding.btnFollowUnfollow.text = "Seguir"
                binding.btnFollowUnfollow.setBackgroundResource(R.drawable.custom_input_rounded_red)
            }
        }

        userViewModel.actionResponse.observe(viewLifecycleOwner) { response ->
            Snackbar.make(binding.root, response.message, Snackbar.LENGTH_SHORT).show()
        }

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
            UserProvider.selectedUser = user
        })

        cardViewModel.cards.observe(viewLifecycleOwner) { cards ->
            if (cards.isNullOrEmpty()) {
                binding.rcvProfileCards.showEmptyView()
            } else {
                binding.rcvProfileCards.hideAllViews()
                binding.rcvProfileCards.recyclerView.adapter = CardAdapter(cards, currentUserId, profileUserId, isEditMode = false, isHome = false, icons = emptyList())
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
    }

    private fun setUpListeners() {
        binding.btnFollowUnfollow.setOnClickListener {
            userViewModel.followUnfollowAction(currentUserId.toInt(), profileUserId.toInt())
        }

        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_profileSettingFragment)
        }

        binding.btnShareProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_shareProfileFragment)
        }

        binding.lytFollowers.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("profileId", profileUserId.toInt())
                putBoolean("isFollowerTab", true)
            }
            findNavController().navigate(R.id.action_profileFragment_to_profileStatsDetailsFragment, bundle)
        }

        binding.lytFolloweds.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("profileId", profileUserId.toInt())
                putBoolean("isFollowerTab", false)
            }
            findNavController().navigate(
                R.id.action_profileFragment_to_profileStatsDetailsFragment,
                bundle
            )
        }

        binding.profileImage.setOnClickListener{
            val dialog = FullScreenImageDialog()
            dialog.show(childFragmentManager, "FullScreenImageDialog")
        }
    }
}