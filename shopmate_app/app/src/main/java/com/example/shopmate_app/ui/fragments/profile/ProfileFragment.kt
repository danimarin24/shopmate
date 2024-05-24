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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentLoginBinding
import com.example.shopmate_app.databinding.FragmentProfileBinding
import com.example.shopmate_app.ui.adapters.BoardAdapter
import com.example.shopmate_app.ui.adapters.CardAdapter
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

    private lateinit var context : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        context = requireContext()

        // Fetch boards for a specific user
        cardViewModel.fetchAllCards(mainViewModel.getUserId()!!)

        setupObservers()

        mainViewModel.getUserId()?.let { profileViewModel.getUserStats(it) }
        mainViewModel.getUserId()?.let { profileViewModel.getUserInformation(it) }


        return binding.root
    }

    private fun setupObservers() {
        profileViewModel.userStatsEntity.observe(viewLifecycleOwner, Observer {userStat ->
            if (userStat == null) {
                //err no existe ningun userStat para el id indicado
                Log.e("ERROR", "ES NULL")
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
        })

        cardViewModel.cards.observe(viewLifecycleOwner) { cards ->
            Log.e("CARDS", cards.toString())
            if (cards.isNullOrEmpty()) {
                binding.rcvProfileCards.showEmptyView()
            } else {
                binding.rcvProfileCards.hideAllViews()
                val currentUserId = "1"// obtener el ID del usuario actual
                val profileUserId = "1"// obtener el ID del perfil que se está viendo
                binding.rcvProfileCards.recyclerView.adapter = CardAdapter(cards, currentUserId, profileUserId)
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