package com.example.shopmate_app.ui.fragments.home

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentHomeBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.ui.adapters.BoardAdapter
import com.example.shopmate_app.ui.adapters.ColorsChoseAdapter
import com.example.shopmate_app.ui.viewmodels.BoardViewModel
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            if (boardList.isNullOrEmpty()) {
                //binding.boxAlertMessage.visibility = View.VISIBLE
                binding.rcvBoardHome.showEmptyView()
            } else {
                //binding.boxAlertMessage.visibility = View.GONE
                binding.rcvBoardHome.hideAllViews()
                binding.rcvBoardHome.recyclerView.adapter = BoardAdapter(boardList)
                binding.rcvBoardHome.recyclerView.layoutManager = LinearLayoutManager(findNavController().context, LinearLayoutManager.VERTICAL, false)

            }
        })

        boardViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.rcvBoardHome.showLoadingView()
            }
        })

        binding.rcvBoardHome.setOnRetryClickListener {
            boardViewModel.getBoardsByOwnerId(mainViewModel.getUserId()!!)
        }

        /*
        binding.btnAction.setOnClickListener {
            showCreateNewBoard()
        }
         */

        return binding.root
    }


}