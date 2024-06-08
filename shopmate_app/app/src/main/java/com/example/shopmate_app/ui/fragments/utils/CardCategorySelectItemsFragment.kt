package com.example.shopmate_app.ui.fragments.utils

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopmate_app.R
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.databinding.FragmentCardCategorySelectItemsBinding
import com.example.shopmate_app.databinding.FragmentCardDetailsViewBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CategoryEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UnitEntity
import com.example.shopmate_app.domain.entities.providers.CardProvider
import com.example.shopmate_app.domain.entities.providers.CategoryProvider
import com.example.shopmate_app.domain.entities.providers.ItemProvider
import com.example.shopmate_app.ui.adapters.CategoryItemAdapter
import com.example.shopmate_app.ui.viewmodels.CardViewModel
import com.example.shopmate_app.ui.viewmodels.CategoryViewModel
import com.example.shopmate_app.ui.viewmodels.ItemViewModel
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.UnitViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardCategorySelectItemsFragment : Fragment() {
    private lateinit var binding: FragmentCardCategorySelectItemsBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val unitViewModel: UnitViewModel by viewModels()
    private val cardViewModel: CardViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val itemsViewModel: ItemViewModel by viewModels()
    
    private var cardId = -1;

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    private var itemClicked : ItemCardLineEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardCategorySelectItemsBinding.inflate(inflater, container, false)

        arguments?.let {
            cardId = it.getInt("cardId", -1)
        }

        unitViewModel.getUnits()
        initUi()
        setUpListeners()
        setUpObservers()

        return binding.root
    }

    private fun initUi() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.standardBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // Show or hide the remaining content based on the state
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.remainingContent.visibility = View.VISIBLE
                } else {
                    binding.remainingContent.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Handle slide offset changes
            }
        })

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


        binding.etNameItem.addTextChangedListener {
            binding.txtItemName.text = binding.etNameItem.text
        }

        binding.btnSave.setOnClickListener {

            var txtAmount = binding.etAmount.text.toString()
            val selectedUnit = binding.cboUnits.selectedItem as UnitEntity

            var txtPrice = binding.etPrice.text.toString()

            if (itemClicked == null) {
                return@setOnClickListener
            }

            if (txtAmount.isNotEmpty()) {
                itemClicked!!.amount = txtAmount.toInt()
            }

            itemClicked!!.unit = selectedUnit
            itemClicked!!.unitId = selectedUnit.unitId

            if (txtPrice.isNotEmpty()){
                itemClicked!!.price = txtPrice.toFloat()
            }

            // modificar el itemcardline con los valores que inserta el usuario:
            itemsViewModel.modifyItemFromACard(itemClicked!!)


        }
    }

    private fun setUpObservers() {

        itemsViewModel.lastItemClicked.observe(viewLifecycleOwner) { lastItem ->
            if (lastItem != null) {
                itemClicked = lastItem
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                binding.etNameItem.setText(lastItem.item?.name)
            }
        }

        itemsViewModel.lastRemovedItem.observe(viewLifecycleOwner) { lastRemovedItem ->
            if (lastRemovedItem != null) {
                itemClicked = null
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }

        unitViewModel.units.observe(viewLifecycleOwner) { units ->
            if (!units.isNullOrEmpty()) {
                val adapter = object : ArrayAdapter<UnitEntity>(
                    findNavController().context,
                    R.layout.cbo_text_list,
                    units
                ) {
                    override fun getView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val view = super.getView(position, convertView, parent)
                        (view as TextView).text = getItem(position)?.prefix
                        return view
                    }

                    override fun getDropDownView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val view = super.getDropDownView(position, convertView, parent)
                        (view as TextView).text = getItem(position)?.prefix
                        return view
                    }
                }
                adapter.setDropDownViewResource(R.layout.cbo_text_list)
                binding.cboUnits.adapter = adapter

                val defaultUnit = units.find { it.unitId == AppConstants.UNIT_ID_UNIT }
                val defaultPosition = units.indexOf(defaultUnit)
                if (defaultPosition >= 0) {
                    binding.cboUnits.setSelection(defaultPosition)
                }
            }
        }
    }

}