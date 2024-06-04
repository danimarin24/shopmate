package com.example.shopmate_app.ui.fragments.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentProfileStatsDetailsBinding
import com.example.shopmate_app.domain.entities.providers.CategoryProvider
import com.example.shopmate_app.domain.entities.providers.ItemProvider
import com.example.shopmate_app.domain.entities.providers.UserProvider
import com.example.shopmate_app.ui.adapters.CategoryItemAdapter
import com.example.shopmate_app.ui.adapters.UserStatsAdapter
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileStatsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProfileStatsDetailsBinding
    private val mainViewModel: MainViewModel by viewModels()

    private val userViewModel: UserViewModel by viewModels()

    private var profileId : Int = 0
    private var isFollowerTab: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileStatsDetailsBinding.inflate(inflater, container, false)

        arguments?.let {
            profileId = it.getInt("profileId", 0)
            isFollowerTab = it.getBoolean("isFollowerTab", true)
        }

        initUi()
        setUpListeners()
        setUpObservers()

        return binding.root
    }

    private fun initUi() {
        setupInitialColors()

        binding.txtHeaderCategoryName.text = UserProvider.selectedUser?.name
    }


    private fun setUpListeners() {
        binding.btnGoBackToProfile.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.checkboxFolloweds.setOnClickListener {
            binding.checkboxFolloweds.isChecked = true
            updateCheckBoxState(binding.checkboxFolloweds, binding.checkboxFollowers, R.color.md_theme_onPrimaryFixedVariant)
            userViewModel.fetchUserFolloweds(profileId)

        }

        binding.checkboxFollowers.setOnClickListener {
            binding.checkboxFollowers.isChecked = true
            updateCheckBoxState(binding.checkboxFollowers, binding.checkboxFolloweds, R.color.md_theme_onPrimaryFixedVariant)
            userViewModel.fetchUserFollowers(profileId)
        }
    }

    private fun setUpObservers() {
        userViewModel.usersFollowers.observe(viewLifecycleOwner) { usersFollowers ->
            if (binding.checkboxFollowers.isChecked && usersFollowers != null) {
                binding.rcvUsers.recyclerView.adapter = UserStatsAdapter(
                    usersFollowers,
                    mainViewModel.getUserId()!!,
                    userViewModel
                )
                binding.rcvUsers.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }

        userViewModel.usersFolloweds.observe(viewLifecycleOwner) {usersFolloweds ->
            if (binding.checkboxFolloweds.isChecked && usersFolloweds != null) {
                binding.rcvUsers.recyclerView.adapter = UserStatsAdapter(
                    usersFolloweds,
                    mainViewModel.getUserId()!!,
                    userViewModel
                )
                binding.rcvUsers.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun setupInitialColors() {
        val initialColor = resources.getColor(R.color.md_theme_onPrimaryFixedVariant)
        val defaultColor = resources.getColor(R.color.md_theme_inversePrimary)

        if (isFollowerTab) {
            binding.checkboxFollowers.isChecked = true
            binding.checkboxFollowers.setTextColor(initialColor)
            binding.checkboxFolloweds.isChecked = false
            binding.checkboxFolloweds.setTextColor(defaultColor)
            userViewModel.fetchUserFollowers(profileId)
        } else {
            binding.checkboxFollowers.isChecked = false
            binding.checkboxFollowers.setTextColor(defaultColor)
            binding.checkboxFolloweds.isChecked = true
            binding.checkboxFolloweds.setTextColor(initialColor)
            userViewModel.fetchUserFolloweds(profileId)

        }
    }

    private fun updateCheckBoxState(
        selectedCheckBox: CheckBox,
        otherCheckBox: CheckBox,
        color: Int
    ) {
        selectedCheckBox.setTextColor(resources.getColor(color))
        otherCheckBox.isChecked = false
        otherCheckBox.setTextColor(resources.getColor(R.color.md_theme_inversePrimary))
    }
}