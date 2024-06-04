package com.example.shopmate_app.ui.fragments.profile

import android.R.id.message
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
            intent.data = Uri.parse("smsto:")
            intent.putExtra("sms_body", "Tu mensaje aqui")

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(findNavController().context, "No hay aplicaciones de SMS instaladas", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSendEmail.setOnClickListener {
            val emailText = binding.etEmail.text
            Toast.makeText(findNavController().context, emailText, Toast.LENGTH_SHORT).show()

            val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailText)) // No funciona, deberia de colocar el email del enviante
            email.putExtra(Intent.EXTRA_SUBJECT, "¡Mira este perfil ShopMate! Disfruta ahora de las mejores ventajas!")
            email.putExtra(Intent.EXTRA_TEXT, "Tu mensaje aqui")
            email.setType("message/rfc822")

            startActivity(Intent.createChooser(email, "Choose an Email client :"))

        }
        return binding.root
    }
}



