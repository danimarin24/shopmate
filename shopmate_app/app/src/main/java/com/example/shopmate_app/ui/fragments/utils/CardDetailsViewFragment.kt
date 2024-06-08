package com.example.shopmate_app.ui.fragments.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
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
import com.example.shopmate_app.ui.viewmodels.UnitViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardDetailsViewFragment : Fragment() {
    private lateinit var binding: FragmentCardDetailsViewBinding
    private val mainViewModel: MainViewModel by viewModels()

    private val cardViewModel: CardViewModel by viewModels()
    private val unitViewModel: UnitViewModel by viewModels()

    private val categoryViewModel: CategoryViewModel by viewModels()
    private val itemsViewModel: ItemViewModel by viewModels()


    private lateinit var itemAdapter: ItemAdapter

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardDetailsViewBinding.inflate(inflater, container, false)

        // Initialize the BottomSheetBehavior
        bottomSheetBehavior = BottomSheetBehavior.from(binding.standardBottomSheet)

        // Set the initial state of the bottom sheet
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        //bottomSheetBehavior.isHideable = false

        // Optionally set a callback to handle state changes and slide offset updates
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // Ensure the bottom sheet is never hidden
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }

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




        binding.txtCardName.text = CardProvider.selectedCard?.cardName
//        binding.tvOwnerId.text = CardProvider.selectedCard?.ownerId.toString()
//        binding.tvEstimatedPrice.text = CardProvider.selectedCard?.estimatedPrice.toString()

        unitViewModel.getUnits()

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
                                var itemCardLineRemoved = ItemCardLineEntity(
                                    itemCardLineId = ItemProvider.lastItemRemoved!!.itemCardLineId,
                                    cardId = ItemProvider.lastItemRemoved!!.cardId,
                                    createdBy = ItemProvider.lastItemRemoved!!.createdBy,
                                    amount = ItemProvider.lastItemRemoved!!.amount,
                                    assignedTo = ItemProvider.lastItemRemoved!!.assignedTo,
                                    price = ItemProvider.lastItemRemoved!!.price,
                                    itemId = ItemProvider.lastItemRemoved!!.itemId,
                                    unitId = ItemProvider.lastItemRemoved!!.unitId
                                )
                                itemsViewModel.addItemToACard(itemCardLineRemoved)
                            }.show()
                    }
                    ItemTouchHelper.RIGHT -> {
                        // Asignar item
                        var assignedUserId = item.assignedTo
                        itemsViewModel.assignItem(item, mainViewModel.getUserId()!!)
                        Snackbar.make(binding.root, "Item asignado", Snackbar.LENGTH_SHORT)
                            .setAction("Deshacer") {
                                itemsViewModel.unassignItem(item, assignedUserId)
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
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.rcvCategories.setOnRetryClickListener {
            Snackbar.make(binding.root, "Cargando categorías", Snackbar.LENGTH_SHORT).show()
            categoryViewModel.fetchCategories()
        }

        binding.etNameItem.addTextChangedListener {
            binding.txtItemName.text = binding.etNameItem.text
        }

        binding.btnSave.setOnClickListener {

            var txtAmount = binding.etAmount.text.toString()
            var txtPrice = binding.etAmount.text.toString()

            var itemCardLineDefault = ItemCardLineEntity(
                itemCardLineId = 0,
                cardId = 0,
                createdBy = 1,
                amount = 1,
                price = 0f,
                assignedTo = 1,
                itemId = 1,
                unitId = AppConstants.UNIT_ID_UNIT)

            if (binding.txtItemName.text.isNotEmpty()) {
                // can add product
                Snackbar.make(binding.root, "Producto añadido", Snackbar.LENGTH_SHORT).show()

                if (txtAmount.isNotEmpty()) {
                    itemCardLineDefault.amount = txtAmount.toInt()
                }
                if (txtPrice.isNotEmpty()){
                    itemCardLineDefault.price = txtPrice.toFloat()
                }

                // crear item sino existe, sino conseguir id del que existe
                // then
                // crear itemcardline con la información adicional si ha puesto el usuario,
                // sino
                // añadir por defecto la amount de "1", y la Unit de "u" (unidad), precio = null
            }
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