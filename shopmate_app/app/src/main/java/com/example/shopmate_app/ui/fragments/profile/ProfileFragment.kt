package com.example.shopmate_app.ui.fragments.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.FragmentLoginBinding
import com.example.shopmate_app.databinding.FragmentProfileBinding
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.ProfileViewModel
import com.example.shopmate_app.utils.PasswordUtils

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var context : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        context = requireContext()

        mainViewModel.getUserId()?.let { profileViewModel.getUserStats(it) }
        mainViewModel.getUserId()?.let { profileViewModel.getUserInformation(it) }

        profileViewModel.userStatsEntity.observe(viewLifecycleOwner, Observer {userStat ->
            if (userStat == null) {
                //err no existe ningun userStat para el id indicado
                Log.e("ERROR", "ES NULL")
                Toast.makeText(context, "No existe ningun userStat para el id indicado", Toast.LENGTH_SHORT).show()
                return@Observer
            }
            binding.txtUserFollows.text = userStat.nfollows.toString()
            binding.txtUserFollowers.text = userStat.nfollowers.toString()
            binding.txtUserTotalSaves.text = userStat.nsaves.toString()
        })

        profileViewModel.userEntity.observe(viewLifecycleOwner, Observer {user ->
            if (user == null) {
                //err no existe ningun userStat para el id indicado
                Log.e("ERROR", "ES NULL")
                Toast.makeText(context, "No existe ningun user para el id indicado", Toast.LENGTH_SHORT).show()
                return@Observer
            }
            var usernameWithAt = "@${user.username}"
            binding.txtUserUsername.text = usernameWithAt
        })

        binding.btnHambMenu.setImageResource(R.drawable.menu)
        binding.btnHambMenu.setOnClickListener {
            binding.btnHambMenu.setImageResource(R.drawable.menu_open)
            showBottomDialog()
        }


        return binding.root
    }

    private fun showBottomDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_profile_dialog)

        val lytSettings = dialog.findViewById<LinearLayout>(R.id.lytSettings)
        val lytShare = dialog.findViewById<LinearLayout>(R.id.lytShare)
        val lytTheme = dialog.findViewById<LinearLayout>(R.id.lytTheme)

        lytSettings.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
        }

        lytShare.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show()
        }

        lytTheme.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(context, "Theme", Toast.LENGTH_SHORT).show()
        }

        binding.btnHambMenu.setOnClickListener { dialog.dismiss() }
        binding.btnHambMenu.setImageResource(R.drawable.menu)


        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}