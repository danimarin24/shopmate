package com.example.shopmate_app.ui.fragments.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentSearchBinding
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.ProfileViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var context : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val checkBoard = binding.checkboxBoard
        val ulBoard = binding.underLineBoard
        val checkUser = binding.checkboxUser
        val ulUser = binding.underLineUser

        context = requireContext()

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
}
