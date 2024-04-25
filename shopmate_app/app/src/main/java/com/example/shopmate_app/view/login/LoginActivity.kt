package com.example.shopmate_app.view.login


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Directory.PACKAGE_NAME
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.shopmate_app.R
import com.example.shopmate_app.api.CrudApi
import com.example.shopmate_app.databinding.ActivityLoginBinding
import com.example.shopmate_app.model.PasswordUtils
import com.example.shopmate_app.view.user.UserProfileActivity
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.String
import java.security.MessageDigest
import java.util.Arrays
import java.util.UUID


class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    private var googleId = "544701638538-ccp0cp41t4r10ofpl8biku4ckh457hm8.apps.googleusercontent.com"


    private var crudApi = CrudApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
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

                val email = googleIdTokenCredential.id
                Log.e("token", googleIdTokenCredential.idToken)

                Log.e("token validado?", crudApi.validateGoogleToken(googleIdTokenCredential.idToken).toString())
                //val googleUser = crudApi.getUserByEmail(email)


                /*
                if (googleUser != null) {
                    // existe
                    signInIntent()
                } else {
                    // registrarlo
                    val name = googleIdTokenCredential.displayName
                    val usernameGenerated = crudApi.getUsernameGenerated(name!!)!!
                    var phoneNumber = googleIdTokenCredential.phoneNumber
                    if (phoneNumber == null) phoneNumber = ""
                    val profileImage = googleIdTokenCredential.profilePictureUri.toString()
                    val googleTokenHashed = PasswordUtils.hashString(googleIdToken)

                    // si no existe, antes de crear el usuario debemos crear sus settings, y sus stats.
                    // Para despues vincularlo al user.

                    val setting : Setting = Setting(null, LocalDate.now().toString(),
                        1u, 1u, 1u, 0u, LocalDate.now().toString(), googleTokenHashed)

                    val stat : Stat = Stat(null, 0u, 0u, 0u, 0u)

                    Log.i("name", name.toString())
                    Log.i("usernameGenerated", usernameGenerated.toString())
                    Log.i("email", email.toString())
                    Log.i("phoneNumber", phoneNumber.toString())
                    Log.i("profileImage", profileImage.toString())
                    Log.i("googleTokenHashed", googleTokenHashed.toString())


                    val user : User = User(null, usernameGenerated, name, null,
                        email, phoneNumber, profileImage, googleTokenHashed, null,
                        LocalDate.now().toString(), LocalDate.now().toString(), 0u, 0u)





                    var userInserted = crudApi.addUserCompleted(setting, stat, user)
                    Log.i("userInserted", userInserted.toString())




                    //crudApi.addUser(googleNewUser)

                }
                 */




                // Puedes acceder a otros campos según la documentación de Google Sign-In


                

                Toast.makeText(context, "You are signed in!", Toast.LENGTH_SHORT).show()
            } catch (e: GetCredentialException) {
                Log.e("GetCredentialException", e.message.toString())
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: GoogleIdTokenParsingException) {
                Log.e("GetCredentialException", e.message.toString())
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signFacebookIn() {

    }

    private fun signEmailIn() {



        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val hashedPassword = PasswordUtils.hashString(password)

        Log.i("PASSWORD HASH", hashedPassword)

        val user = crudApi.getUser("danimarin24")

        if (user!!.password == hashedPassword) {
            user.name?.let { Log.i("USER NAME", it) }
            user.email?.let { Log.i("USER EMAIL", it) }
            user.password?.let { Log.i("USER PASSWORD HASH", it) }
        }


    }


    private fun signInIntent() {
        val intent = Intent(this, UserProfileActivity::class.java)
        startActivity(intent)
    }
}