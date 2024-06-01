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
import androidx.activity.viewModels
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
import com.example.shopmate_app.ui.viewmodels.CardViewModel
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.ProfileViewModel
import com.example.shopmate_app.ui.viewmodels.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val boardViewModel: BoardViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by viewModels({ requireActivity() })


    private lateinit var context : Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        context = requireContext()

        setupObservers()

        // Fetch boards for a specific user
        boardViewModel.fetchBoards(mainViewModel.getUserId()!!)

        binding.rcvBoardHome.setOnRetryClickListener {
            boardViewModel.fetchBoards(mainViewModel.getUserId()!!)
        }

        return binding.root
    }

    private fun setupObservers() {
        sharedViewModel.boards.observe(viewLifecycleOwner) { boards ->
            Log.d("HomeFragment", "sharedViewModel.boards observed: $boards")

            if (boards.isNullOrEmpty()) {
                binding.rcvBoardHome.showEmptyView()
            } else {
                binding.rcvBoardHome.hideAllViews()
                sharedViewModel.cardsByBoard.observe(viewLifecycleOwner) { cardsByBoard ->
                    binding.rcvBoardHome.recyclerView.adapter = BoardAdapter(boards, cardsByBoard)
                    binding.rcvBoardHome.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }
        }

        boardViewModel.boards.observe(viewLifecycleOwner) { boards ->
            if (boards.isNullOrEmpty()) {
                binding.rcvBoardHome.showEmptyView()
            } else {
                binding.rcvBoardHome.hideAllViews()
                boardViewModel.cardsByBoard.observe(viewLifecycleOwner) { cardsByBoard ->
                    binding.rcvBoardHome.recyclerView.adapter = BoardAdapter(boards, cardsByBoard)
                    binding.rcvBoardHome.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }
        }

        boardViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.rcvBoardHome.showLoadingView()
            }
        }

        boardViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                binding.rcvBoardHome.showErrorView()
            }
        }
    }


}