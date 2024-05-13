package com.example.shopmate_app.ui.fragments.template

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentHomeBinding
import com.example.shopmate_app.databinding.FragmentTemplateBinding
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.ProfileViewModel

class TemplateFragment : Fragment() {
    private lateinit var binding: FragmentTemplateBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var context : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTemplateBinding.inflate(inflater, container, false)
        context = requireContext()

        return binding.root
    }

}