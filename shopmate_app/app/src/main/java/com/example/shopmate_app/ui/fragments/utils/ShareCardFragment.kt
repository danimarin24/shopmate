package com.example.shopmate_app.ui.fragments.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopmate_app.R
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.databinding.FragmentShareCardBinding
import com.example.shopmate_app.databinding.FragmentShareProfileBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.CardShareLinkRequestEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkRequestEntity
import com.example.shopmate_app.domain.entities.providers.CardProvider
import com.example.shopmate_app.ui.viewmodels.CardViewModel
import com.example.shopmate_app.ui.viewmodels.ShareViewModel
import com.example.shopmate_app.ui.viewmodels.UserViewModel
import com.google.android.material.snackbar.Snackbar

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareCardFragment : Fragment() {
    private lateinit var binding: FragmentShareCardBinding

    private val shareViewModel: ShareViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShareCardBinding.inflate(inflater,container,false)

        val shareLinkReq = CardShareLinkRequestEntity(CardProvider.selectedCard!!.cardId, AppConstants.ROLE_GUEST)
        shareViewModel.generateCardShareLinkToken(shareLinkReq)

        shareViewModel.shareLink.observe(viewLifecycleOwner) { link ->
            binding.tvShareWhatsapp.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.setPackage("com.whatsapp")
                intent.putExtra(Intent.EXTRA_TEXT, createMessage(link))

                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Snackbar.make(binding.root, "No esta instalado Whatsapp", Snackbar.LENGTH_SHORT).show()
                }
            }

            binding.tvShareFacebook.setOnClickListener  {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.setPackage("com.facebook.katana")
                intent.putExtra(Intent.EXTRA_TEXT, createMessage(link))

                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Snackbar.make(binding.root, "No esta instalado Facebook", Snackbar.LENGTH_SHORT).show()

                }
            }

            binding.etEmailLayout.setEndIconOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("smsto:")
                intent.putExtra("sms_body", createMessage(link))

                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Snackbar.make(binding.root, "No hay aplicaciones de SMS instaladas", Snackbar.LENGTH_SHORT).show()

                }
            }

            binding.btnSendEmail.setOnClickListener {
                val emailText = binding.etEmail.text
                Toast.makeText(findNavController().context, emailText, Toast.LENGTH_SHORT).show()

                val email = Intent(Intent.ACTION_SEND)
                email.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailText)) // No funciona, deberia de colocar el email del enviante
                email.putExtra(Intent.EXTRA_SUBJECT, "¡Mira este lista ShopMate!")
                email.putExtra(Intent.EXTRA_TEXT, createMessage(link))
                email.setType("message/rfc822")

                startActivity(Intent.createChooser(email, "Choose an Email client :"))

            }
        }



        return binding.root
    }

    fun createMessage(link: String): String {
        return "¡Hola! Te invito a compartir una lista de compras de ShopMate!. Es muy fácil, solo debes acceder a este enlace: $link"
    }
}