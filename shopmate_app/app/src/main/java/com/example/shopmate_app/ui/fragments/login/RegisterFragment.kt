package com.example.shopmate_app.ui.fragments.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shopmate_app.R
import com.example.shopmate_app.model.api.CrudApi
import com.example.shopmate_app.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private var crudApi = CrudApi()
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


            val user = crudApi.getUserByEmail(userEmail)

            if (user != null) {
                Toast.makeText(context, "Ya existe un usuario con esta dirección", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            var action = RegisterFragmentDirections.actionRegisterFragmentToRegisterProfileFragment(userEmail)
            findNavController().navigate(action)
        }

        return binding.root
    }
}