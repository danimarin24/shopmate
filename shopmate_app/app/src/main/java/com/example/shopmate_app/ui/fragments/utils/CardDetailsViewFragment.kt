package com.example.shopmate_app.ui.fragments.utils

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.databinding.FragmentCardDetailsViewBinding
import com.example.shopmate_app.domain.entities.providers.CardProvider
import com.example.shopmate_app.ui.viewmodels.CardViewModel
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardDetailsViewFragment : Fragment() {
    private lateinit var binding: FragmentCardDetailsViewBinding
    private val mainViewModel: MainViewModel by viewModels()

    private val cardViewModel: CardViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardDetailsViewBinding.inflate(inflater, container, false)

        binding.txtCardName.text = CardProvider.selectedCard?.cardName
//        binding.tvOwnerId.text = CardProvider.selectedCard?.ownerId.toString()
//        binding.tvEstimatedPrice.text = CardProvider.selectedCard?.estimatedPrice.toString()

        cardViewModel.fetchCategoriesIconsByCard(CardProvider.selectedCard?.cardId!!)
        setUpObvservers()


        return binding.root
    }

    private fun setUpObvservers() {
        cardViewModel.categoriesIcons.observe(viewLifecycleOwner) {icons ->
            if (icons.isNotEmpty()) {
                loadCategoryIcons(icons)
            }

        }
    }

    private fun loadCategoryIcons(iconUrls: List<String>) {
        val sizeInDp = 20 // Tamaño en dp
        val sizeInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, sizeInDp.toFloat(), resources.displayMetrics).toInt()

        for (url in iconUrls) {
            val imageView = ImageView(findNavController().context)
            val layoutParams = LinearLayout.LayoutParams(sizeInPx, sizeInPx)
            layoutParams.setMargins(0, 0, 8, 0) // Márgenes opcionales
            imageView.layoutParams = layoutParams

            // Utiliza una biblioteca como Glide para cargar la imagen desde una URL
            Glide.with(this)
                .load("${AppConstants.BASE_API_URL}${url}")
                .into(imageView)

            binding.categoryIconsContainer.addView(imageView)
        }
    }
}