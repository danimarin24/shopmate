package com.example.shopmate_app.ui.fragments.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shopmate_app.databinding.FragmentRegisterProfileBinding

class RegisterProfileFragment : Fragment() {
    private lateinit var binding: FragmentRegisterProfileBinding
    val args: RegisterProfileFragmentArgs by navArgs()
    private lateinit var context : Context
    private lateinit var userEmail : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userEmail = args.email
        binding.etEmail.setText(userEmail)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterProfileBinding.inflate(inflater, container, false)

        context = requireContext()

        binding.btnSave.setOnClickListener {
            val userName = binding.etName.text.toString()
            val isNameValid = ValidatorUtils.nameValidation(userName, context)

            if (!isNameValid) {
                // err no ha pasado los test de validacion
                return@setOnClickListener
            }

            var action = RegisterProfileFragmentDirections.actionRegisterProfileFragmentToRegisterUsernameFragment(userEmail, userName)
            findNavController().navigate(action)
        }

        return binding.root
    }
}