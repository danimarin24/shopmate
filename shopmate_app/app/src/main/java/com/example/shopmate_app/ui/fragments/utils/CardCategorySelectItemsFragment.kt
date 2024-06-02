package com.example.shopmate_app.ui.fragments.utils

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentCardCategorySelectItemsBinding
import com.example.shopmate_app.databinding.FragmentCardDetailsViewBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.CategoryEntity
import com.example.shopmate_app.domain.entities.providers.CardProvider
import com.example.shopmate_app.domain.entities.providers.CategoryProvider
import com.example.shopmate_app.domain.entities.providers.ItemProvider
import com.example.shopmate_app.ui.adapters.CategoryItemAdapter
import com.example.shopmate_app.ui.viewmodels.CardViewModel
import com.example.shopmate_app.ui.viewmodels.CategoryViewModel
import com.example.shopmate_app.ui.viewmodels.ItemViewModel
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardCategorySelectItemsFragment : Fragment() {
    private lateinit var binding: FragmentCardCategorySelectItemsBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val cardViewModel: CardViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val itemsViewModel: ItemViewModel by viewModels()
    
    private var cardId = -1;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardCategorySelectItemsBinding.inflate(inflater, container, false)

        arguments?.let {
            cardId = it.getInt("cardId", -1)
        }

        initUi()
        setUpListeners()
        setUpObservers()

        return binding.root
    }

    private fun initUi() {
        binding.txtHeaderCategoryName.text = CategoryProvider.selectedCategory?.name

        if (cardId != -1 && CategoryProvider.selectedCategory?.items?.isNotEmpty() == true) {
            Log.e("items", ItemProvider.items.toString())
            binding.rcvItemsOfCurrentCategory.recyclerView.adapter = CategoryItemAdapter(
                CategoryProvider.selectedCategory?.items!!,
                cardId,
                mainViewModel.getUserId()!!,
                ItemProvider.items.map { it.item!!.itemId }.toSet(),
                itemsViewModel
            )
            binding.rcvItemsOfCurrentCategory.recyclerView.layoutManager = GridLayoutManager(findNavController().context, 3, GridLayoutManager.VERTICAL, false)
        }
    }


    private fun setUpListeners() {
        binding.btnGoBackToCard.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpObservers() {
        //TODO("Not yet implemented")
    }

}