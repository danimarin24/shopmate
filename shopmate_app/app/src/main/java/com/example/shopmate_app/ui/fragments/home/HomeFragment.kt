package com.example.shopmate_app.ui.fragments.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.shopmate_app.databinding.FragmentHomeBinding
import com.example.shopmate_app.ui.viewmodels.BoardViewModel
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.ProfileViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val boardViewModel: BoardViewModel by viewModels()

    private lateinit var context : Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        context = requireContext()



        boardViewModel.getBoardsByOwnerId(mainViewModel.getUserId()!!)

        boardViewModel.boardsEntity.observe(viewLifecycleOwner, Observer { boardList ->
            if (boardList != null) {
                binding.boxAlertNoBoardFound.visibility = View.GONE
            } else {
                binding.boxAlertNoBoardFound.visibility = View.VISIBLE
            }
        })

        return binding.root
    }
}