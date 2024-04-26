package com.example.shopmate_app.controller

import android.content.Context
import android.widget.Toast
import com.example.shopmate_app.api.CrudApi

class ValidatorUtils {
    companion object {
        fun emailValidation(email: String, context: Context) : Boolean {
            if (email == "") {
                Toast.makeText(context, "El email esta vacio", Toast.LENGTH_SHORT).show()
                return false
            }

            if (!email.contains("@")) {
                Toast.makeText(context, "El email no contiene @", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }
    }
}