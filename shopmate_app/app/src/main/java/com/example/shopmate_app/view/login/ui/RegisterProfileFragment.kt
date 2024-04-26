package com.example.shopmate_app.view.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentRegisterProfileBinding

private const val ARG_USER_EMAIL = "userEmail"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterProfileFragment : Fragment() {
    private var userEmail: String? = null
    private lateinit var binding: FragmentRegisterProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentRegisterProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        arguments?.let {
            userEmail = it.getString(ARG_USER_EMAIL)
        }

        if (userEmail != null) {
            binding.etEmail.setText(userEmail)
        }

        binding.btnSave.setOnClickListener {

        }

        binding.lblConfigLater.setOnClickListener {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterProfileBinding.inflate(layoutInflater)
        if (userEmail != null) {
            binding.etEmail.setText(userEmail)
        }
        return binding.root
    }
}