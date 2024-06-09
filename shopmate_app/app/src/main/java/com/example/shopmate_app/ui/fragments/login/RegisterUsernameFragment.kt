package com.example.shopmate_app.ui.fragments.login

import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.PickVisualMediaRequest.Builder
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.shopmate_app.databinding.FragmentRegisterUsernameBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.ui.viewmodels.UserViewModel
import com.example.shopmate_app.utils.ValidatorUtils
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class RegisterUsernameFragment : Fragment() {
    private lateinit var binding: FragmentRegisterUsernameBinding
    private val userViewModel: UserViewModel by viewModels()
    val args: RegisterUsernameFragmentArgs by navArgs()
    private lateinit var context : Context

    private lateinit var userEmail : String
    private var userImage : String? = null
    private lateinit var userName : String
    private lateinit var userUsername : String

    private var imageURI: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterUsernameBinding.inflate(inflater, container, false)

        context = requireContext()

        userEmail = args.email
        userName = args.name
        userImage = args.image
        imageURI = Uri.parse(args.imageUri)

        setupClickListeners()

        initUi()

        return binding.root
    }

    private fun initUi() {
        if (userImage == null) {
            binding.editIcon.visibility = View.VISIBLE
            binding.ivProfilePicker.setOnClickListener(profilePickerClickListener)
            binding.editIcon.setOnClickListener(profilePickerClickListener)
        }
        else {
            Glide.with(findNavController().context).load(imageURI).into(binding.ivProfilePicker)
            binding.editIcon.visibility = View.GONE
            binding.ivProfilePicker.setOnClickListener {}
            binding.editIcon.setOnClickListener {}
        }
    }

    val profilePickerClickListener = View.OnClickListener {
        pickMedia.launch(
            PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                .build()
        )
    }

    private fun setupClickListeners() {
        binding.btnConfirm.setOnClickListener {
            userUsername = binding.etUsername.text.toString()
            Log.i("userUsername", userUsername)

            val isUserUsernameValidated = ValidatorUtils.usernameValidation(userUsername, context)

            if (!isUserUsernameValidated) {
                // err no ha pasado los test de validacion
                return@setOnClickListener
            }


            userViewModel.getUserByUsername(userUsername)

            var userEntity : UserEntity? = null
            userViewModel.userEntity.observe(viewLifecycleOwner, Observer {user ->
                userEntity = user
            })
            if (userEntity != null) {
                Snackbar.make(binding.root, "A user with this username already exists.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userImage == null && imageURI != null) {
                val imageFile = getFileFromUri(imageURI)
                if (imageFile != null && imageFile.exists()) {
                    userImage = imageFile.absolutePath
                }
            }

            var action = RegisterUsernameFragmentDirections.actionRegisterUsernameFragmentToRegisterSecurityFragment(userEmail, userName, userUsername, userImage,
                imageURI.toString()
            )
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