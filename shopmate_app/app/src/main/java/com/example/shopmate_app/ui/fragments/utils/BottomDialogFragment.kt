package com.example.shopmate_app.ui.fragments.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopmate_app.R
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.databinding.BottomDialogLayoutBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.UnitEntity
import com.example.shopmate_app.ui.viewmodels.UnitViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BottomDialogLayoutBinding

    private val unitViewModel: UnitViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomDialogLayoutBinding.inflate(inflater, container, false)

        unitViewModel.getUnits()
        setUpObvservers()
        setUpListeners()




        return binding.root
    }

    private fun setUpObvservers() {
        unitViewModel.units.observe(this) { units ->
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


    private fun setUpListeners() {
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



    companion object {
        const val TAG = "BottomDialogFragment"
    }
}
