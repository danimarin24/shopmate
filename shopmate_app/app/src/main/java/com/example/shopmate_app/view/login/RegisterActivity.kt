package com.example.shopmate_app.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shopmate_app.R
import com.example.shopmate_app.api.CrudApi
import com.example.shopmate_app.controller.ValidatorUtils.Companion.emailValidation
import com.example.shopmate_app.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding

    private var crudApi = CrudApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val isEmailValidated = emailValidation(email, this)

            if (!isEmailValidated) {
                // err no ha pasado los test de validacion
                return@setOnClickListener
            }


            val user = crudApi.getUserByEmail(email)

            if (user != null) {
                Toast.makeText(this, "Ya existe un usuario con esta dirección", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, RegisterStepsActivity::class.java)
            val bundle = Bundle()
            bundle.putString("userEmail", email)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }


}