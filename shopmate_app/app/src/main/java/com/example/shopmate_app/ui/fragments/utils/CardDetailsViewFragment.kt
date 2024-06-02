package com.example.shopmate_app.ui.fragments.utils

import android.app.Dialog
import android.content.ClipData.Item
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopmate_app.R
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.databinding.FragmentCardDetailsViewBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CategoryEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UnitEntity
import com.example.shopmate_app.domain.entities.providers.CardProvider
import com.example.shopmate_app.domain.entities.providers.ItemProvider
import com.example.shopmate_app.ui.adapters.CategoryAdapter
import com.example.shopmate_app.ui.adapters.ItemAdapter
import com.example.shopmate_app.ui.viewmodels.CardViewModel
import com.example.shopmate_app.ui.viewmodels.CategoryViewModel
import com.example.shopmate_app.ui.viewmodels.ItemViewModel
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardDetailsViewFragment : Fragment() {
    private lateinit var binding: FragmentCardDetailsViewBinding
    private val mainViewModel: MainViewModel by viewModels()

    private val cardViewModel: CardViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val itemsViewModel: ItemViewModel by viewModels()


    private lateinit var itemAdapter: ItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardDetailsViewBinding.inflate(inflater, container, false)

        binding.txtCardName.text = CardProvider.selectedCard?.cardName
//        binding.tvOwnerId.text = CardProvider.selectedCard?.ownerId.toString()
//        binding.tvEstimatedPrice.text = CardProvider.selectedCard?.estimatedPrice.toString()

        categoryViewModel.fetchCategories()
        cardViewModel.fetchCategoriesIconsByCard(CardProvider.selectedCard?.cardId!!)

        itemsViewModel.fetchAllItems(CardProvider.selectedCard?.cardId!!)

        itemAdapter = ItemAdapter(emptyList())

        setUpObvservers()
        setUpRecyclerView()
        setUpListeners()


        return binding.root
    }

    private fun setUpObvservers() {
        cardViewModel.categoriesIcons.observe(viewLifecycleOwner) {icons ->
            if (icons.isNotEmpty()) {
                loadCategoryIcons(icons)
            }
        }

        itemsViewModel.items.observe(viewLifecycleOwner) { items ->
            if (items.isNullOrEmpty()) {
                binding.rcvCurrentItems.showEmptyView("Vaya no hay ningún item assignado a esta lista.")
            } else {
                binding.rcvCurrentItems.hideAllViews()
                itemAdapter = ItemAdapter(items)
                binding.rcvCurrentItems.recyclerView.adapter = itemAdapter
            }
        }

        categoryViewModel.categories.observe(viewLifecycleOwner) { categories ->
            if (categories.isNullOrEmpty()) {
                binding.rcvCategories.showEmptyView("Vaya no hay ninguna categoría creada.")
            } else {
                binding.rcvCategories.hideAllViews()
                binding.rcvCategories.recyclerView.adapter =
                    CategoryAdapter(categories, CardProvider.selectedCard?.cardId!!)
                binding.rcvCategories.recyclerView.layoutManager =
                    LinearLayoutManager(findNavController().context)
            }
        }

        categoryViewModel.isError.observe(viewLifecycleOwner) { err ->
            if (err)
                binding.rcvCategories.showErrorView("Ha habído un problema al encontrar categorías...")
        }

        categoryViewModel.isError.observe(viewLifecycleOwner) { loading ->
            if (loading)
                binding.rcvCategories.showLoadingView()
        }
    }

    private fun setUpRecyclerView() {
        binding.rcvCurrentItems.recyclerView.layoutManager = LinearLayoutManager(findNavController().context)

        val itemTouchHelper = ItemTouchHelper(object : SwipeToDeleteCallback(requireContext()) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = itemAdapter.getItem(position)

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        // Eliminar item
                        itemsViewModel.removeItemFromACard(item)
                        Snackbar.make(binding.root, "Item eliminado", Snackbar.LENGTH_SHORT)
                            .setAction("Deshacer") {
                                itemsViewModel.addItemToACard(ItemProvider.lastItemRemoved!!)
                            }.show()
                    }
                    ItemTouchHelper.RIGHT -> {
                        // Asignar item
                        itemsViewModel.assignItem(item)
                        Snackbar.make(binding.root, "Item asignado", Snackbar.LENGTH_SHORT)
                            .setAction("Deshacer") {
                                itemsViewModel.unassignItem(item)
                            }.show()
                    }
                }
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.rcvCurrentItems.recyclerView)
    }

    private fun setUpListeners() {
        binding.btnCardSettings.setOnClickListener {
            showSettingsCardBottomDialog()
        }

        binding.btnAddNewItem.setOnClickListener {
            val bottomDialogFragment = BottomDialogFragment()
            bottomDialogFragment.show(childFragmentManager, BottomDialogFragment.TAG)
        }

        binding.rcvCategories.setOnRetryClickListener {
            Snackbar.make(binding.root, "Cargando categorías", Snackbar.LENGTH_SHORT).show()
            categoryViewModel.fetchCategories()
        }
    }

    private fun loadCategoryIcons(iconUrls: List<String>) {
        val sizeInDp = 20 // Tamaño en dp
        val sizeInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, sizeInDp.toFloat(), resources.displayMetrics).toInt()

        binding.categoryIconsContainer.removeAllViews()

        for (url in iconUrls) {
            val imageView = ImageView(findNavController().context)
            val layoutParams = LinearLayout.LayoutParams(sizeInPx, sizeInPx)
            layoutParams.setMargins(0, 0, 8, 0)
            imageView.layoutParams = layoutParams

            Glide.with(this)
                .load("${AppConstants.BASE_API_URL}${url}")
                .into(imageView)

            binding.categoryIconsContainer.addView(imageView)
        }
    }

    private fun showSettingsCardBottomDialog() {
        val dialog = Dialog(findNavController().context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_card_options_dialog)

        val lytShareCard = dialog.findViewById<LinearLayout>(R.id.lytShareCard)
        val lytSettingsCard = dialog.findViewById<LinearLayout>(R.id.lytSettingsCard)
        val lytPrintCard = dialog.findViewById<LinearLayout>(R.id.lytPrintCard)
        val lytMembersConfig = dialog.findViewById<LinearLayout>(R.id.lytMembersConfig)
        val lytRecommendAFriend = dialog.findViewById<LinearLayout>(R.id.lytRecommendAFriend)


        lytShareCard.setOnClickListener {
            dialog.dismiss()
            Snackbar.make(binding.root, "Sharing card...", Snackbar.LENGTH_SHORT).show()
        }

        lytSettingsCard.setOnClickListener {
            dialog.dismiss()
            Snackbar.make(binding.root, "Settings card...", Snackbar.LENGTH_SHORT).show()
        }

        lytPrintCard.setOnClickListener {
            dialog.dismiss()
            Snackbar.make(binding.root, "Print card...", Snackbar.LENGTH_SHORT).show()
        }

        lytMembersConfig.setOnClickListener {
            dialog.dismiss()
            Snackbar.make(binding.root, "Members from this card...", Snackbar.LENGTH_SHORT).show()
        }

        lytRecommendAFriend.setOnClickListener {
            dialog.dismiss()
            Snackbar.make(binding.root, "Recommend ShopMate to a friend...", Snackbar.LENGTH_SHORT).show()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}