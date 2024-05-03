package com.example.shopmate_app.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.shopmate_app.controller.DataStoreManager
import com.example.shopmate_app.databinding.ActivitySplashBinding
import com.example.shopmate_app.model.MainViewModel
import com.example.shopmate_app.view.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this@SplashActivity)[MainViewModel::class.java]
        dataStoreManager = DataStoreManager(this)

        checkThemeMode()

        if (checkLogged()) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish()
    }

    private fun checkLogged(): Boolean {
        Log.e("userId", "aofdsafojasdghfds")
        var isLogged = false

        lifecycleScope.launch(Dispatchers.IO){
            isLogged = viewModel.getUserId != null
        }

        return isLogged
    }

    private fun checkThemeMode() {
        binding.apply {
            viewModel.getTheme.observe(this@SplashActivity) { isDarkMode ->
                when (isDarkMode) {
                    true -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    false -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }
    }
}