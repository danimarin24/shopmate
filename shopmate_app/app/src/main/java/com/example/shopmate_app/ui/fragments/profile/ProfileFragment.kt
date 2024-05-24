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


    private lateinit var context : Context


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        context = requireContext()

        setupObservers()

        arguments?.let {
            profileId = it.getInt("profileId", 0)
        }

        Log.e("profileID", profileId.toString())

        if (profileId != 0) {
            // Fetch boards for a specific user
            cardViewModel.fetchAllCards(profileId)
            profileViewModel.getUserStats(profileId)
            profileViewModel.getUserInformation(profileId)
        } else {
            // Fetch boards for a specific user
            cardViewModel.fetchAllCards(mainViewModel.getUserId()!!)
            mainViewModel.getUserId()?.let { profileViewModel.getUserStats(it) }
            mainViewModel.getUserId()?.let { profileViewModel.getUserInformation(it) }
        }

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
            Log.e("CARDS", cards.toString())
            if (cards.isNullOrEmpty()) {
                binding.rcvProfileCards.showEmptyView()
            } else {
                binding.rcvProfileCards.hideAllViews()

                var currentUserId = "1" // obtener el ID del usuario actual
                var profileUserId = "1" // obtener el ID del perfil que se está viendo
                if (profileId != 0) {
                    currentUserId = mainViewModel.getUserId().toString()
                    profileUserId = profileId.toString()
                } else {
                    currentUserId = mainViewModel.getUserId().toString()
                    profileUserId = mainViewModel.getUserId().toString()
                }

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
}