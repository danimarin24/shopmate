package com.example.shopmate_app.ui.fragments.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.shopmate_app.databinding.FragmentRegisterSecurityBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.ui.activities.MainActivity
import com.example.shopmate_app.ui.viewmodels.UserViewModel
import com.example.shopmate_app.utils.PasswordUtils
import java.time.LocalDate

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterSecurityFragment : Fragment() {
    private lateinit var binding: FragmentRegisterSecurityBinding
    private val userViewModel: UserViewModel by viewModels()
    private val args: RegisterSecurityFragmentArgs by navArgs()

    private lateinit var context : Context

    private lateinit var userEmail : String
    private lateinit var userName : String
    private lateinit var userUsername : String
    private lateinit var userHashedPassword : String
    private lateinit var userPassword : String
    private lateinit var userConfirmPassword : String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userEmail = args.email
        userName = args.name
        userUsername = args.username
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterSecurityBinding.inflate(inflater, container, false)

        context = requireContext()


        binding.btnSave.setOnClickListener {
            userPassword = binding.etPassword.text.toString()
            userConfirmPassword = binding.etConfirmPassword.text.toString()

            Log.i("pass", userPassword)
            Log.i("pass1", userConfirmPassword)

            if (userPassword != userConfirmPassword) {
                Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            Log.i("userEmail", userEmail)
            Log.i("userName", userName)
            Log.i("userUsername", userUsername)
            Log.i("userPassword", userPassword)

            userHashedPassword = PasswordUtils.hashString(userPassword)

            val user = UserEntity(null, userUsername, userName, userHashedPassword, userEmail, "",
                "", null, null, LocalDate.now().toString(), LocalDate.now().toString(), 0, 0)

            var addedUser: UserEntity? = null
            userViewModel.addUser(user)
            userViewModel.userEntity.observe(viewLifecycleOwner) { user ->
                addedUser = user
            }

            if (addedUser != null) {
                // user added
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            } else {
                // user not added,
            }
        }

        return binding.root
    }
}