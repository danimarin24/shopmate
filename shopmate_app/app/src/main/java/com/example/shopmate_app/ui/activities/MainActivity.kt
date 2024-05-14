package com.example.shopmate_app.ui.activities

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navListener: NavController.OnDestinationChangedListener
    private lateinit var context : Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = applicationContext

        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostMainFragmentContainer.id) as NavHostFragment
        navListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.homeFragment -> {
                    changeHeaderInfo("settings")
                }

            }
            false

            Log.e("PROFILE ID", R.id.profileFragment.toString())
            Log.e("DESTINATION ID", destination.id.toString())
            Log.e("DESTINATION LABEL", destination.label.toString())
            Log.e("DESTINATION navigator name", destination.navigatorName)
        }
        navController = navHostFragment.navController

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    changeHeaderInfo("home")
                    navController.navigate(R.id.homeFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.search -> {
                    changeHeaderInfo("search")
                    navController.navigate(R.id.searchFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.templates -> {
                    changeHeaderInfo("template")
                    navController.navigate(R.id.templateFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.profile -> {
                    changeHeaderInfo("profile")
                    navController.navigate(R.id.profileFragment)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        // INIT
        binding.bottomNavigationBar.menu.findItem(R.id.profile).setChecked(true)
        changeHeaderInfo("profile")
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(navListener)
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(navListener)
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.navHostMainFragmentContainer)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    private fun changeHeaderInfo(activeFragment: String) {
        binding.btnLeft.setImageResource(android.R.color.transparent)
        binding.btnLeft.setOnClickListener{}
        binding.btnRight.setImageResource(android.R.color.transparent)
        binding.btnRight.setOnClickListener{}

        when (activeFragment) {
            "home" -> {
                binding.txtHeaderTitle.text = getString(R.string.strHome)
            }
            "search" -> {
                binding.txtHeaderTitle.text = getString(R.string.strSearch)
            }
            "template" -> {
                binding.txtHeaderTitle.text = getString(R.string.strTemplate)
            }
            "profile" -> {
                binding.txtHeaderTitle.text = getString(R.string.strProfile)

                binding.btnRight.setImageResource(R.drawable.menu)
                binding.btnRight.setOnClickListener {
                    binding.btnRight.setImageResource(R.drawable.menu_open)
                    showSettingsBottomDialog()
                }
            }
            "settings" -> {
                binding.txtHeaderTitle.text = getString(R.string.strProfileSettings)
            }
        }

    }

    private fun showSettingsBottomDialog() {
        val dialog = Dialog(navController.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_profile_dialog)

        val lytSettings = dialog.findViewById<LinearLayout>(R.id.lytSettings)
        val lytShare = dialog.findViewById<LinearLayout>(R.id.lytShare)
        val lytTheme = dialog.findViewById<LinearLayout>(R.id.lytTheme)

        lytSettings.setOnClickListener {
            dialog.dismiss()
            navController.navigate(R.id.action_profileFragment_to_profileSettingFragment)
            Toast.makeText(navController.context, "Settings", Toast.LENGTH_SHORT).show()
        }

        lytShare.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(navController.context, "Share", Toast.LENGTH_SHORT).show()
        }

        lytTheme.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(navController.context, "Theme", Toast.LENGTH_SHORT).show()
        }

        dialog.setOnDismissListener {
            binding.btnRight.setImageResource(R.drawable.menu)
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}