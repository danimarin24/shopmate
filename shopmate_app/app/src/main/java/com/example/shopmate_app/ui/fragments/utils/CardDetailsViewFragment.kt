package com.example.shopmate_app.ui.fragments.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentCardDetailsViewBinding
import com.example.shopmate_app.databinding.FragmentTemplateBinding
import com.example.shopmate_app.domain.entities.providers.CardProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailsViewFragment : Fragment() {
    private lateinit var binding: FragmentCardDetailsViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardDetailsViewBinding.inflate(inflater, container, false)

//        binding.tvCardTitle.text = CardProvider.selectedCard?.cardName
//        binding.tvOwnerId.text = CardProvider.selectedCard?.ownerId.toString()
//        binding.tvEstimatedPrice.text = CardProvider.selectedCard?.estimatedPrice.toString()

        return binding.root
    }
}