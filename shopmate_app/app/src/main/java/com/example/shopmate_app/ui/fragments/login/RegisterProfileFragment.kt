package com.example.shopmate_app.ui.fragments.login

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.shopmate_app.databinding.FragmentRegisterProfileBinding
import com.example.shopmate_app.utils.ValidatorUtils
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class RegisterProfileFragment : Fragment() {
    private lateinit var binding: FragmentRegisterProfileBinding
    val args: RegisterProfileFragmentArgs by navArgs()
    private lateinit var context : Context
    private lateinit var userEmail : String
    private var userImage : String? = null
    private var imageURI: Uri? = null


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

        setupClickListeners()




        return binding.root
    }

    private fun setupClickListeners() {
        val profilePickerClickListener = View.OnClickListener {
            pickMedia.launch(
                PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    .build()
            )
        }

        binding.ivProfilePicker.setOnClickListener(profilePickerClickListener)
        binding.editIcon.setOnClickListener(profilePickerClickListener)

        binding.btnSave.setOnClickListener {
            val userName = binding.etName.text.toString()
            val isNameValid = ValidatorUtils.nameValidation(userName, context)

            if (!isNameValid) {
                // err no ha pasado los test de validacion
                return@setOnClickListener
            }

            if (imageURI != null) {
                val imageFile = getFileFromUri(imageURI)
                if (imageFile != null && imageFile.exists()) {
                    userImage = imageFile.absolutePath
                }
            }

            var action = RegisterProfileFragmentDirections.actionRegisterProfileFragmentToRegisterUsernameFragment(userEmail, userName, userImage, imageURI.toString())
            findNavController().navigate(action)
        }
    }

    var pickMedia: ActivityResultLauncher<PickVisualMediaRequest> = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            imageURI = uri
            Glide.with(findNavController().context).load(uri).into(binding.ivProfilePicker)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    private fun getFileFromUri(uri: Uri?): File? {
        if (uri == null) return null
        return try {
            val inputStream = context?.contentResolver?.openInputStream(uri)
            val mimeType = context?.contentResolver?.getType(uri)
            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
            val tempFile = File(context?.cacheDir, "temp_image.$extension")
            inputStream?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            tempFile
        } catch (e: Exception) {
            Log.e("getFileFromUri", "Error converting URI to file", e)
            null
        }
    }
}