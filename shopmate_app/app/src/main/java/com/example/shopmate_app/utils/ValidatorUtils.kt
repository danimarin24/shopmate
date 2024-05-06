package com.example.shopmate_app.utils

import android.content.Context
import android.widget.Toast
import com.example.shopmate_app.model.api.CrudApi

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


        fun nameValidation(name: String, context: Context) : Boolean {
            if (name == "") {
                Toast.makeText(context, "El nombre esta vacio", Toast.LENGTH_SHORT).show()
                return false
            }

            if (name.length > 35) {
                Toast.makeText(context, "El nombre es muy largo, maximo 35 caracteres", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }

        fun usernameValidation(username: String, context: Context) : Boolean {
            if (username == "") {
                Toast.makeText(context, "El nombre de usuario esta vacio", Toast.LENGTH_SHORT).show()
                return false
            }

            if (username.length > 20) {
                Toast.makeText(context, "El nombre de usuario es muy largo, maximo 20 caracteres", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }
    }
}