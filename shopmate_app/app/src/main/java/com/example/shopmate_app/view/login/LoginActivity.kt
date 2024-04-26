package com.example.shopmate_app.view.login


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.shopmate_app.R
import com.example.shopmate_app.api.CrudApi
import com.example.shopmate_app.databinding.ActivityLoginBinding
import com.example.shopmate_app.controller.PasswordUtils
import com.example.shopmate_app.controller.PasswordUtils.Companion.checkPassword
import com.example.shopmate_app.model.Setting
import com.example.shopmate_app.model.Stat
import com.example.shopmate_app.model.User
import com.example.shopmate_app.view.user.UserProfileActivity
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.time.LocalDate
import java.util.UUID


class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    private var googleId = "544701638538-ccp0cp41t4r10ofpl8biku4ckh457hm8.apps.googleusercontent.com"


    private var crudApi = CrudApi()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Ocultar la barra de navegación
        hideSystemUI()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //checkLogged()


        // LISTENERS
        binding.btnGoogle.setOnClickListener {
            signGoogleIn()
        }

        binding.btnFacebook.setOnClickListener {
            signFacebookIn()
        }

        binding.btnLogin.setOnClickListener {
            signEmailIn()
        }

        binding.txtForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }


    // A function to check if user is already Login
    // redirect to home page.
    private fun checkLogged() {




        val isLogged = true;

        if (isLogged) {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }
    }





    @RequiresApi(Build.VERSION_CODES.O)
    private fun signGoogleIn() {
        val context = this@LoginActivity

        val credentialManager = CredentialManager.create(context)

        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(googleId)
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        var googleUser : User?
        var googleName : String = ""
        var googleUsernameGenerated : String = ""
        var googleEmail : String = ""
        var googlePhoneNumber : String? = ""
        var googleSub : String = ""
        var googleProfileImage : String = ""
        var googleSubHashed : String = ""

        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)

                var googleTokenSub = crudApi.validateGoogleToken(googleIdTokenCredential.idToken)

                if (googleTokenSub == null) {
                    return@launch
                }

                googleName = googleIdTokenCredential.displayName.toString()
                googleEmail = googleIdTokenCredential.id
                googlePhoneNumber = googleIdTokenCredential.phoneNumber
                googleProfileImage = googleIdTokenCredential.profilePictureUri.toString()
                googleSubHashed = PasswordUtils.hashString(googleTokenSub)

                Toast.makeText(context, "You are signed in!", Toast.LENGTH_SHORT).show()
            } catch (e: GetCredentialException) {
                Log.e("GetCredentialException", e.message.toString())
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: GoogleIdTokenParsingException) {
                Log.e("GetCredentialException", e.message.toString())
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }

            googleUser = crudApi.getUserByGoogleSub(googleSubHashed)
            if (googleUser == null) {
                // error no existe un usuario, hay que crearlo
                val setting : Setting = Setting(null, LocalDate.now().toString(),
                    1u, 1u, 1u, 0u, LocalDate.now().toString(), googleSubHashed)

                val stat : Stat = Stat(null, 0u, 0u, 0u, 0u)

                // generar username
                googleUsernameGenerated = crudApi.getUsernameGenerated(googleName)!!

                // user has setting and stat id set to null, because in addUserCompleted method, we will assign the correct new ones.
                val user : User = User(null, googleUsernameGenerated, googleName, null,
                    googleEmail, googlePhoneNumber, googleProfileImage, googleSubHashed, null,
                    LocalDate.now().toString(), LocalDate.now().toString(), 0u, 0u)

                val userInserted = crudApi.addUserCompleted(setting, stat, user)
                Log.i("userInserted", userInserted.toString())
            }
            // existe, y esta verificado
            signInIntent()
        }
    }

    private fun signFacebookIn() {

    }

    private fun signEmailIn() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val hashedPassword = PasswordUtils.hashString(password)

        val user = crudApi.getUser("danimarin24")

        if (checkPassword(user!!.password!!, hashedPassword)) {
            // existe el usuario, y su contraseña coincide, inciciar sesión
            signInIntent()
        }



    }


    private fun signInIntent() {
        val intent = Intent(this, UserProfileActivity::class.java)
        startActivity(intent)
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController
            controller?.hide(WindowInsetsCompat.Type.navigationBars())
            controller?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
        }
    }
}