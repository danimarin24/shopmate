package com.example.shopmate_app.ui.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.shopmate_app.databinding.FragmentProfileSettingChangePasswordBinding
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.UserViewModel
import com.example.shopmate_app.utils.PasswordUtils
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileSettingChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentProfileSettingChangePasswordBinding
    private val userViewModel: UserViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileSettingChangePasswordBinding.inflate(inflater,container, false)


        binding.btnSave.setOnClickListener {
            var actualPass = binding.etFirstPasswordPassword.text.toString()
            var newPass = binding.etPassword.text.toString()
            var newPassConfirm = binding.etConfirmPassword.text.toString()
            var canChange = false;

            if (actualPass.isNullOrEmpty()) {
                binding.etFirstPasswordPassword.hint = "SABES ESCRIBIR"

                // Mensaje falta password
            } else {
                userViewModel.getUserByUserId(mainViewModel.getUserId()!!)

                userViewModel.userEntity.observe(viewLifecycleOwner) { user ->
                    if (user != null) {
                        if (PasswordUtils.checkPasswordHash(actualPass, user.password!!)) {
                            canChange = true
                        }
                        Snackbar.make(binding.root, "Contraseña incorrecta", Snackbar.LENGTH_SHORT).show()
                    }

                    if (canChange) {
                        if (newPass.isNullOrEmpty() || newPassConfirm.isNullOrEmpty()) {
                        // Esta vacio
                        } else {

                            if (!newPass.equals(newPassConfirm)) {
                                // Las contraseñas no coinciden

                            } else {
                                user?.password = PasswordUtils.hashString(newPass)
                                userViewModel.putUser(user!!)
                            }
                        }
                    }
                }


            }
        }



        return binding.root
    }

}