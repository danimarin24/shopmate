package com.example.shopmate_app.ui.fragments.utils

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.util.Log
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
import androidx.core.content.FileProvider
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


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
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
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

        itemAdapter = ItemAdapter()
        binding.rcvCurrentItems.recyclerView.adapter = itemAdapter

        setUpObvservers()
        setUpRecyclerView()
        setUpListeners()


        return binding.root
    }

    private fun setUpObvservers() {
        cardViewModel.categoriesIcons.observe(viewLifecycleOwner) { icons ->
            if (icons.isNotEmpty()) {
                loadCategoryIcons(icons)
            }
        }

        itemsViewModel.items.observe(viewLifecycleOwner) { items ->
            if (items.isNullOrEmpty()) {
                binding.rcvCurrentItems.showEmptyView("Vaya no hay ningún item assignado a esta lista.")
            } else {
                binding.rcvCurrentItems.hideAllViews()
                itemAdapter.submitList(items)
                itemAdapter.notifyDataSetChanged()
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
        binding.rcvCurrentItems.recyclerView.layoutManager =
            LinearLayoutManager(findNavController().context)

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
                unitId = AppConstants.UNIT_ID_UNIT
            )

            if (binding.txtItemName.text.isNotEmpty()) {
                // can add product
                Snackbar.make(binding.root, "Producto añadido", Snackbar.LENGTH_SHORT).show()

                if (txtAmount.isNotEmpty()) {
                    itemCardLineDefault.amount = txtAmount.toInt()
                }
                if (txtPrice.isNotEmpty()) {
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
            TypedValue.COMPLEX_UNIT_DIP, sizeInDp.toFloat(), resources.displayMetrics
        ).toInt()

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
            generatePdfFromRecyclerView(itemAdapter.getCurrentList())
            Snackbar.make(binding.root, "Print card...", Snackbar.LENGTH_SHORT).show()
        }

        lytMembersConfig.setOnClickListener {
            dialog.dismiss()
            Snackbar.make(binding.root, "Members from this card...", Snackbar.LENGTH_SHORT).show()
        }

        lytRecommendAFriend.setOnClickListener {
            dialog.dismiss()
            Snackbar.make(binding.root, "Recommend ShopMate to a friend...", Snackbar.LENGTH_SHORT)
                .show()
        }

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    private fun generatePdfFromRecyclerView(items: List<ItemCardLineEntity>) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        var currentPage = pdfDocument.startPage(pageInfo)
        var canvas = currentPage.canvas

        var yPosition = 25

        // Definir ancho de columnas
        val columnWidths = floatArrayOf(3.5f, 1.5f, 1.5f) // Ajusta según tus necesidades
        val tableWidth = 300f

        val table = PdfTable(columnWidths, tableWidth)

        // Agregar encabezados
        table.addCell(PdfCell().addText("Item").setBackgroundColor(Color.LTGRAY))
        table.addCell(PdfCell().addText("Amount").setBackgroundColor(Color.LTGRAY))
        table.addCell(PdfCell().addText("Price").setBackgroundColor(Color.LTGRAY))

        // Añadir filas de datos
        for (item in items) {
            table.addCell(PdfCell().addText(item.item?.name ?: ""))
            table.addCell(PdfCell().addText(item.amount.toString()))
            table.addCell(PdfCell().addText(item.price.toString()))
        }

        // Calcular total de precios
        val totalPrice = items.sumOf { it.price.toDouble() } // Asegúrate de que price es convertible a Double

        // Dibujar encabezados y datos de la tabla
        yPosition += table.drawHeaders(tableWidth.toInt(), canvas, 10f, yPosition)
        yPosition = table.draw(tableWidth.toInt(), canvas, 10f, yPosition, pdfDocument, pageInfo, 25)

        // Dibujar la línea final con el total
        if (yPosition + 40 > canvas.height) {
            pdfDocument.finishPage(currentPage)
            currentPage = pdfDocument.startPage(pageInfo)
            canvas = currentPage.canvas
            yPosition = 25
        }
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 14f
        canvas.drawLine(10f, (yPosition + 10).toFloat(), tableWidth - 10, (yPosition + 10).toFloat(), paint)
        canvas.drawText("Total: $totalPrice", tableWidth - 80, (yPosition + 30).toFloat(), paint)

        // Finalizar la página actual
        pdfDocument.finishPage(currentPage)

        // Guardar el PDF en un archivo
        val file = File(requireContext().getExternalFilesDir(null), "CardItems.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Snackbar.make(
                binding.root,
                "PDF generado en: ${file.absolutePath}",
                Snackbar.LENGTH_LONG
            ).show()
            showPdfFile(file.absolutePath)
        } catch (e: IOException) {
            e.printStackTrace()
            Snackbar.make(binding.root, "Error al generar el PDF", Snackbar.LENGTH_SHORT).show()
        } finally {
            pdfDocument.close()
        }
    }

    private fun showPdfFile(filePath: String) {
        val file = File(filePath)
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(uri, "application/pdf")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Snackbar.make(binding.root, "No se encontró visor de PDF", Snackbar.LENGTH_SHORT).show()
        }
    }

    private class PdfTable(private val columnWidths: FloatArray, private val tableWidth: Float) {

        private val cellPadding = 10f // Ajusta según tus necesidades

        private val cells = mutableListOf<PdfCell>()
        var totalHeight = 0
        var headerHeight = 0

        fun addCell(cell: PdfCell): PdfTable {
            cells.add(cell)
            return this
        }

        fun drawHeaders(tableWidth: Int, canvas: Canvas, xPosition: Float, yPosition: Int): Int {
            var currentX = xPosition
            var currentY = yPosition.toFloat()

            headerHeight = 0

            for (i in 0 until columnWidths.size) {
                val width = columnWidths[i] * (tableWidth / columnWidths.sum())
                val pdfCell = cells[i]
                pdfCell.calculateHeight(width - 2 * cellPadding)

                if (pdfCell.height > headerHeight) {
                    headerHeight = pdfCell.height.toInt()
                }

                pdfCell.draw(
                    currentX + cellPadding,
                    currentY + cellPadding,
                    width - 2 * cellPadding,
                    canvas
                )
                currentX += width
            }

            return headerHeight + (2 * cellPadding).toInt()
        }

        fun draw(
            tableWidth: Int,
            canvas: Canvas,
            xPosition: Float,
            yPosition: Int,
            pdfDocument: PdfDocument,
            pageInfo: PdfDocument.PageInfo,
            initialYPosition: Int
        ): Int {
            var currentX = xPosition
            var currentY = yPosition.toFloat()

            var maxHeightInRow = 0f

            // Calcular el ancho total de las columnas
            val totalColumnWidth = columnWidths.sum()

            for ((index, pdfCell) in cells.withIndex()) {
                if (index % columnWidths.size == 0 && index != 0) {
                    currentY += maxHeightInRow + 2 * cellPadding
                    currentX = xPosition
                    maxHeightInRow = 0f

                    if (currentY + maxHeightInRow + 2 * cellPadding > canvas.height) {
                        // Finalizar la página actual
                        pdfDocument.finishPage(pdfDocument.startPage(pageInfo))

                        // Iniciar una nueva página
                        currentY = initialYPosition.toFloat()

                        // Dibujar encabezados en la nueva página
                        currentY += drawHeaders(tableWidth, canvas, xPosition, initialYPosition)
                    }
                }

                val width =
                    columnWidths[index % columnWidths.size] * (tableWidth / totalColumnWidth)
                pdfCell.calculateHeight(width - 2 * cellPadding)
                if (pdfCell.height > maxHeightInRow) {
                    maxHeightInRow = pdfCell.height
                }

                // Dibujar la celda
                pdfCell.draw(
                    currentX + cellPadding,
                    currentY + cellPadding,
                    width - 2 * cellPadding,
                    canvas
                )

                currentX += width
            }

            totalHeight = (currentY + maxHeightInRow - yPosition).toInt()
            return totalHeight
        }
    }

    // Clase PdfCell que crea las celdas de la tabla dentro del documento PDF
    private class PdfCell {
        private val paint = Paint()
        private val textPaint = Paint()

        var height = 0f

        init {
            paint.style = Paint.Style.STROKE
            textPaint.textSize = 12f // Ajusta según tus necesidades
        }

        fun addText(text: String): PdfCell {
            this.text = text
            return this
        }

        fun setBackgroundColor(color: Int): PdfCell {
            this.color = color
            return this
        }

        fun calculateHeight(width: Float) {
            val textBounds = Rect()
            textPaint.getTextBounds(text, 0, text.length, textBounds)
            height = textBounds.height().toFloat()
        }

        fun draw(x: Float, y: Float, width: Float, canvas: Canvas) {
            paint.color = color
            canvas.drawRect(x, y, x + width, y + height, paint)
            canvas.drawText(text, x, y + height, textPaint)
        }

        private var text: String = ""
        private var color: Int = Color.WHITE
    }
}