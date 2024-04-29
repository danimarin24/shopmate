package com.example.shopmate_app.view.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentRegisterBinding
import com.example.shopmate_app.databinding.FragmentRegisterProfileBinding
import org.w3c.dom.Text

class RegisterProfileFragment : Fragment() {
    private lateinit var binding: FragmentRegisterProfileBinding
    val args: RegisterProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userEmail = args.userEmail
        binding.etEmail.setText(userEmail)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterProfileBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener {
            findNavController().navigate(R.id.action_registerProfileFragment_to_registerUsernameFragment)
        }


        return binding.root
    }
}