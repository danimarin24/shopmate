package com.example.shopmate_app.ui.fragments.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentProfileBinding
import com.example.shopmate_app.databinding.FragmentShareProfileBinding
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareProfileFragment : Fragment() {

    private lateinit var binding: FragmentShareProfileBinding
    private val userViewModel: UserViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShareProfileBinding.inflate(inflater,container,false)
        userViewModel.getUserByUserId(mainViewModel.getUserId()!!)
        userViewModel.userEntity.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.lblUsername.text = "@"+user.username
            }
            }

        binding.tvShareWhatsapp.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.setPackage("com.whatsapp")
            intent.putExtra(Intent.EXTRA_TEXT, "Tu mensaje aquí")

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(findNavController().context, "No esta instalado Whatsapp", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvShareFacebook.setOnClickListener  {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.setPackage("com.facebook.katana")
            intent.putExtra(Intent.EXTRA_TEXT, "Tu mensaje aquí")

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(findNavController().context, "No esta instalado Facebook", Toast.LENGTH_SHORT).show()
            }

        }

        binding.etEmailLayout.setEndIconOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("smsto:") // Esto asegura que solo las apps de SMS puedan manejar el Intent
            intent.putExtra("sms_body", "Tu mensaje aqui")

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(findNavController().context, "No hay aplicaciones de SMS instaladas", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root

    }
    }



