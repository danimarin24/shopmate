package com.example.shopmate_app.ui.fragments.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentRegisterBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.ui.viewmodels.UserViewModel
import com.example.shopmate_app.utils.ValidatorUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val userViewModel: UserViewModel by viewModels()

    private lateinit var context : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        context = requireContext()

        binding.txtLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnNext.setOnClickListener {
            val userEmail = binding.etEmail.text.toString()
            val isEmailValidated = ValidatorUtils.emailValidation(userEmail, context)

            if (!isEmailValidated) {
                // err no ha pasado los test de validacion
                return@setOnClickListener
            }

            var normalUser: UserEntity? = null
            userViewModel.getUserByEmail(userEmail)
            userViewModel.userEntity.observe(viewLifecycleOwner) { user ->
                normalUser = user
            }

            if (normalUser != null) {
                Toast.makeText(context, "Ya existe un usuario con esta dirección", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var action = RegisterFragmentDirections.actionRegisterFragmentToRegisterProfileFragment(userEmail)
            findNavController().navigate(action)
        }

        return binding.root
    }
}