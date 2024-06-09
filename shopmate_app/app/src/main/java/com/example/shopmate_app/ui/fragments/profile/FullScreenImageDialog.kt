package com.example.shopmate_app.ui.fragments.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.shopmate_app.R
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.databinding.FragmentFullScreenImageDialogBinding
import com.example.shopmate_app.databinding.FragmentProfileBinding
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.blurry.Blurry

@AndroidEntryPoint
class FullScreenImageDialog: DialogFragment() {
    private lateinit var binding: FragmentFullScreenImageDialogBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private var profileId: Int = 0

    private lateinit var currentUserId: String
    private lateinit var profileUserId: String

    private lateinit var context: Context

    companion object {
        private const val ARG_PROFILE_ID = "profileId"

        fun newInstance(profileId: Int): FullScreenImageDialog {
            val args = Bundle()
            args.putInt(ARG_PROFILE_ID, profileId)
            val fragment = FullScreenImageDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFullScreenImageDialogBinding.inflate(inflater, container, false)
        context = requireContext()

        arguments?.let {
            profileId = it.getInt("profileId", 0)
        }

        if (profileId != 0) {
            currentUserId = mainViewModel.getUserId().toString()
            profileUserId = profileId.toString()
            profileViewModel.getUserStats(profileId)
            profileViewModel.getUserInformation(profileId)
        } else {
            currentUserId = mainViewModel.getUserId().toString()
            profileUserId = mainViewModel.getUserId().toString()
            profileViewModel.getUserStats(mainViewModel.getUserId()!!)
            profileViewModel.getUserInformation(mainViewModel.getUserId()!!)
        }

        setupObservers()

        binding.fullScreenImageView.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    private fun setupObservers() {

        profileViewModel.userEntity.observe(viewLifecycleOwner, Observer { user ->
            if (user == null) {
                //err no existe ningun userStat para el id indicado
                Log.e("ERROR", "ES NULL")
                Toast.makeText(
                    context,
                    "No existe ningun user para el id indicado",
                    Toast.LENGTH_SHORT
                ).show()
                return@Observer
            }


            if (user.googleToken.isNullOrEmpty()) {
                Glide.with(findNavController().context)
                    .load("${AppConstants.BASE_API_URL}${user.profileImage}")
                    .into(binding.fullScreenImageView)
            } else {
                Glide.with(findNavController().context)
                    .load(user.profileImage)
                    .into(binding.fullScreenImageView)
            }
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Blurry effect
        val rootView = activity?.window?.decorView?.rootView
        rootView?.let {
            it.post {
                val screenshot = Bitmap.createBitmap(it.width, it.height, Bitmap.Config.ARGB_8888)
                val canvas = android.graphics.Canvas(screenshot)
                it.draw(canvas)

                binding.blurryBackground.visibility = View.VISIBLE
                Blurry.with(context).radius(25).from(screenshot).into(binding.blurryBackground)
            }
        }

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}
