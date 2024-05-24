package com.example.shopmate_app.ui.fragments.profile

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentProfileBinding
import com.example.shopmate_app.databinding.FragmentProfileSettingBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.ui.activities.LoginActivity
import com.example.shopmate_app.ui.adapters.ColorsChoseAdapter
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileSettingFragment : Fragment() {
    private lateinit var binding: FragmentProfileSettingBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileSettingBinding.inflate(inflater,container, false)

        binding.txtChangePass.setOnClickListener {
            findNavController().navigate(R.id.action_profileSettingFragment_to_profileSettingChangePasswordFragment)
        }
        binding.txtLogout.setOnClickListener {
            showLogoutDialog()
        }
        binding.txtProfile.setOnClickListener {
            showEditProfile()
        }
        return binding.root
    }

    fun showEditProfile() {
        val dialog = Dialog(findNavController().context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_profile_edit_dialog)

        val ivProfilePicker = dialog.findViewById<ImageView>(R.id.ivProfilePicker)
        val etPassword = dialog.findViewById<TextInputEditText>(R.id.etPassword)
        val etUsername = dialog.findViewById<TextInputEditText>(R.id.etUsername)
        val etName = dialog.findViewById<TextInputEditText>(R.id.etName)
        val btnSave = dialog.findViewById<Button>(R.id.btnSave)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)

        ivClose.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {

        }



        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(findNavController().context)
        builder.setTitle("Cerrar Sesión")
        builder.setMessage("¿Estás seguro que quieres cerrar sesión?")
        builder.setPositiveButton("Sí") { dialog, which ->
            logout()
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun logout() {
       mainViewModel.clearData()
        startActivity(Intent(findNavController().context,LoginActivity::class.java))
    }

}
