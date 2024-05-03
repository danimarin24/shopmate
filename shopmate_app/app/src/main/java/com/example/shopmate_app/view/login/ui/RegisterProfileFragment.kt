package com.example.shopmate_app.view.login.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shopmate_app.R
import com.example.shopmate_app.controller.ValidatorUtils
import com.example.shopmate_app.databinding.FragmentRegisterBinding
import com.example.shopmate_app.databinding.FragmentRegisterProfileBinding
import org.w3c.dom.Text

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