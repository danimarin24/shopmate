package com.example.shopmate_app.model

import java.security.MessageDigest
import java.math.BigInteger


class PasswordUtils {
    companion object {
        // Función para hashear una cadena utilizando SHA-256
        fun hashString(input: String): String {
            val md = MessageDigest.getInstance("SHA-256")
            val messageDigest = md.digest(input.toByteArray())
            val no = BigInteger(1, messageDigest)
            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            return hashtext
        }

        // Función para verificar si la contraseña ingresada coincide con la contraseña hasheada almacenada
        fun checkPassword(inputPassword: String, storedHashedPassword: String): Boolean {
            val hashedInputPassword = hashString(inputPassword)
            return hashedInputPassword == storedHashedPassword
        }
    }
}