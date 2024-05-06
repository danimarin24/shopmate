package com.example.shopmate_app.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.shopmate_app.databinding.ActivitySplashBinding
import com.example.shopmate_app.ui.viewmodels.MainViewModel

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkLogged()) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish()
    }

    private fun checkLogged(): Boolean {
        return mainViewModel.getUserId() != null
    }
}