package com.example.shopmate_app.ui.fragments.utils

import android.content.ClipData.Item
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.BottomDialogLayoutBinding
import com.example.shopmate_app.databinding.FragmentCardDetailsViewBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.ItemCardLineEntity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BottomDialogLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomDialogLayoutBinding.inflate(inflater, container, false)

        binding.etNameItem.addTextChangedListener {
            binding.txtItemName.text = binding.etNameItem.text
        }

        binding.btnSave.setOnClickListener {
            if (binding.txtItemName.text.isNotEmpty()) {
                // can add product
                Snackbar.make(binding.root, "Producto añadido", Snackbar.LENGTH_SHORT).show()

                // crear item sino existe, sino conseguir id del que existe
                // then
                // crear itemcardline con la información adicional si ha puesto el usuario,
                // sino
                // añadir por defecto la amount de "1", y la Unit de "u" (unidad), precio = null

                ItemCardLineEntity(
                    itemCardLineId = 0,
                    cardId = 0,
                    createdBy = 1,
                    amount = 1,
                    price = 0f,
                    assignedTo = 1,
                    itemId = 1,
                    unitId = 9)

            }
        }

        return binding.root
    }

    companion object {
        const val TAG = "BottomDialogFragment"
    }
}
