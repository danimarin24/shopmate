package com.example.shopmate_app.ui.fragments.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentSearchBinding
import com.example.shopmate_app.ui.adapters.BoardAdapter
import com.example.shopmate_app.ui.adapters.CardAdapter
import com.example.shopmate_app.ui.viewmodels.BoardViewModel
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.ProfileViewModel
import com.example.shopmate_app.ui.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var context : Context

    private val boardViewModel: BoardViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val checkBoard = binding.checkboxBoard
        val ulBoard = binding.underLineBoard
        val checkUser = binding.checkboxUser
        val ulUser = binding.underLineUser
        val editTextSearch = binding.etSearch

        context = requireContext()

        setupObservers()
        setupInitialColors()

        checkBoard.setOnClickListener {
            if (!checkBoard.isChecked) {
                checkBoard.isChecked = true
            }
            updateCheckBoxState(checkBoard, ulBoard, checkUser, ulUser, R.color.md_theme_onPrimaryFixedVariant)
        }


        checkUser.setOnClickListener {
            if (!checkUser.isChecked) {
                checkUser.isChecked = true
            }
            updateCheckBoxState(checkUser, ulUser, checkBoard, ulBoard, R.color.md_theme_onPrimaryFixedVariant)
        }
        binding.etSearchLayout.setEndIconOnClickListener {
            val searchText = binding.etSearch.text.toString()
            if (searchText.isNotEmpty()) {
                if (checkBoard.isChecked) {
                    Toast.makeText(context, "board buscador", Toast.LENGTH_SHORT).show()
                    searchViewModel.searchCards(searchText)
                } else if (checkUser.isChecked){
                    Toast.makeText(context, "user buscador", Toast.LENGTH_SHORT).show()
                    searchViewModel.searchUsers(searchText)
                }
            } else {
                Toast.makeText(context, "Please enter a search term", Toast.LENGTH_SHORT).show()
            }


        }

        return binding.root
    }

    private fun setupInitialColors() {

        val initialColor = resources.getColor(R.color.md_theme_onPrimaryFixedVariant)
        val defaultColor = resources.getColor(R.color.md_theme_inversePrimary)

        binding.checkboxBoard.isChecked = true
        binding.checkboxBoard.setTextColor(initialColor)
        binding.underLineBoard.setBackgroundColor(initialColor)

        binding.checkboxUser.isChecked = false
        binding.checkboxUser.setTextColor(defaultColor)
        binding.underLineUser.setBackgroundColor(defaultColor)
    }

    private fun updateCheckBoxState(
        selectedCheckBox: CheckBox, selectedUnderline: View,
        otherCheckBox: CheckBox, otherUnderline: View,
        color: Int
    ) {

        selectedCheckBox.setTextColor(resources.getColor(color))
        selectedUnderline.setBackgroundColor(resources.getColor(color))

        otherCheckBox.isChecked = false
        resetCheckBoxState(otherCheckBox, otherUnderline, R.color.md_theme_inversePrimary)
    }

    private fun resetCheckBoxState(checkBox: CheckBox, underline: View, color: Int) {
        checkBox.setTextColor(resources.getColor(color))
        underline.setBackgroundColor(resources.getColor(color))
    }

    private fun setupObservers() {
        binding.rcvSearch.showEmptyView()

        searchViewModel.searchCardStatsEntity.observe(viewLifecycleOwner) { cards ->
            if (cards.isNullOrEmpty()) {
                binding.rcvSearch.showEmptyView()
            } else {
                binding.rcvSearch.hideAllViews()
                binding.rcvSearch.recyclerView.adapter = CardAdapter(cards, "1", "1")
                binding.rcvSearch.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }

        searchViewModel.searchUsersStatsEntity.observe(viewLifecycleOwner) { users ->
            if (users.isNullOrEmpty()) {
                binding.rcvSearch.showEmptyView()
            } else {
                binding.rcvSearch.hideAllViews()
                // binding.rcvSearch.recyclerView.adapter = UserAdapter(users)
                binding.rcvSearch.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }


        searchViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.rcvSearch.showLoadingView()
            }
        }

        searchViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                binding.rcvSearch.showErrorView()
            }
        }
    }
}
