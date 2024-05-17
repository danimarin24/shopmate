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
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentHomeBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
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
                binding.boxAlertMessage.visibility = View.VISIBLE
            } else {
                binding.boxAlertMessage.visibility = View.GONE
            }
        })

        binding.btnAction.setOnClickListener {
            showCreateNewBoard()
        }



        return binding.root
    }

    fun showCreateNewBoard() {
        val dialog = Dialog(findNavController().context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_board_create_dialog)

        val txtCancel = dialog.findViewById<MaterialTextView>(R.id.txtBoardCancel)
        val txtCreate = dialog.findViewById<MaterialTextView>(R.id.txtBoardCreate)
        val etBoardNameLyt = dialog.findViewById<TextInputLayout>(R.id.etBoardNameLyt)
        val etBoardName = dialog.findViewById<TextInputEditText>(R.id.etBoardName)


        etBoardNameLyt.setEndIconOnClickListener {
            etBoardName.text?.clear()
        }

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }

        txtCreate.setOnClickListener {
            if (etBoardName.text.isNullOrBlank()) {
                //err is null or empty/blank
                Snackbar.make(binding.root, getString(R.string.errNameInvalid), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var board = BoardEntity(null, etBoardName.text.toString(), mainViewModel.getUserId()!!)
            boardViewModel.addBoard(board);
            boardViewModel.getBoardsByOwnerId(mainViewModel.getUserId()!!)
            dialog.dismiss()
        }

        /*
        dialog.setOnDismissListener {
            binding.btnRight.setImageResource(R.drawable.menu)
        }
         */

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}