package com.example.shopmate_app.ui.fragments.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shopmate_app.model.api.CrudApi
import com.example.shopmate_app.databinding.FragmentRegisterUsernameBinding


class RegisterUsernameFragment : Fragment() {
    private lateinit var binding: FragmentRegisterUsernameBinding
    val args: RegisterUsernameFragmentArgs by navArgs()
    private var crudApi = CrudApi()
    private lateinit var context : Context

    private lateinit var userEmail : String
    private lateinit var userName : String
    private lateinit var userUsername : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterUsernameBinding.inflate(inflater, container, false)

        context = requireContext()

        userEmail = args.email
        userName = args.name
        Log.i("userEmail", userEmail)
        Log.i("userName", userName)

        binding.btnConfirm.setOnClickListener {
            userUsername = binding.etUsername.text.toString()
            Log.i("userUsername", userUsername)

            val isUserUsernameValidated = ValidatorUtils.usernameValidation(userUsername, context)

            if (!isUserUsernameValidated) {
                // err no ha pasado los test de validacion
                return@setOnClickListener
            }


            val user = crudApi.getUserByUsername(userUsername)

            if (user != null) {
                Toast.makeText(context, "Ya existe un usuario con este nombre de usuario.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            var action = RegisterUsernameFragmentDirections.actionRegisterUsernameFragmentToRegisterSecurityFragment(userEmail, userName, userUsername)
            findNavController().navigate(action)
        }

        return binding.root
    }
}