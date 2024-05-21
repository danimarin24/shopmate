package com.example.shopmate_app.ui.fragments.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentProfileBinding
import com.example.shopmate_app.databinding.FragmentProfileSettingBinding
import com.example.shopmate_app.ui.adapters.ColorsChoseAdapter
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class ProfileSettingFragment : Fragment() {
    private lateinit var binding: FragmentProfileSettingBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileSettingBinding.inflate(inflater,container, false)

        binding.txtChangePass.setOnClickListener {
            findNavController().navigate(R.id.action_profileSettingFragment_to_profileSettingChangePasswordFragment)
        }

        return binding.root
    }

}
