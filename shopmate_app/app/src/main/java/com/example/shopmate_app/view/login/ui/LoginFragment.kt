package com.example.shopmate_app.view.login.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shopmate_app.R
import com.example.shopmate_app.controller.DataStoreManager
import com.example.shopmate_app.model.api.CrudApi
import com.example.shopmate_app.controller.PasswordUtils
import com.example.shopmate_app.databinding.FragmentLoginBinding
import com.example.shopmate_app.model.MainViewModel
import com.example.shopmate_app.model.api.User
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.time.LocalDate
import java.util.UUID

class LoginFragment : Fragment() {
    private var googleId = "544701638538-ccp0cp41t4r10ofpl8biku4ckh457hm8.apps.googleusercontent.com"

    private lateinit var binding: FragmentLoginBinding
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var viewModel: MainViewModel

    private var crudApi = CrudApi()

    private lateinit var context : Context

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        context = requireContext()
        viewModel = ViewModelProvider(this@LoginFragment)[MainViewModel::class.java]
        dataStoreManager = DataStoreManager(context)

        binding.btnGoogle.setOnClickListener {
            signGoogleIn()
        }

        binding.txtRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.txtForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun signGoogleIn() {

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

                // Aquí puedes realizar las operaciones que dependen de los datos obtenidos de Google
                googleUser = crudApi.getUserByGoogleSub(googleSubHashed)
                if (googleUser == null) {
                    // error no existe un usuario, hay que crearlo

                    // generar username
                    googleUsernameGenerated = crudApi.getUsernameGenerated(googleName)!!

                    // user has setting and stat id set to null, because in the api, we will create automatically a new setting and a new stat.
                    val user : User = User(null, googleUsernameGenerated, googleName, null,
                        googleEmail, googlePhoneNumber, googleProfileImage, googleSubHashed, null,
                        LocalDate.now().toString(), LocalDate.now().toString(), 0, 0)

                    val userInserted = crudApi.addUser(user)
                    Log.i("userInserted", userInserted.toString())

                    binding.apply {
                        lifecycleScope.launch {
                            viewModel.setUserId(userInserted!!.userId!!)
                        }
                    }

                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                    }

                } else {
                    binding.apply {
                        lifecycleScope.launch {
                            viewModel.setUserId(googleUser!!.userId!!)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                    }
                }

            } catch (e: GetCredentialException) {
                Log.e("GetCredentialException", e.message.toString())
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: GoogleIdTokenParsingException) {
                Log.e("GetCredentialException", e.message.toString())
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
            catch (e: Exception) {
                Log.e("Exception", e.message.toString())
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signFacebookIn() {

    }

    /*
    private fun signEmailIn() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val hashedPassword = PasswordUtils.hashString(password)

        val user = crudApi.getUser("danimarin24")

        if (PasswordUtils.checkPasswordHash(user!!.password!!, hashedPassword)) {
            // existe el usuario, y su contraseña coincide, inciciar sesión
            signInIntent()
        }



    }
     */

    private fun signInIntent() {
        //val intent = Intent(this, UserProfileActivity::class.java)
        //startActivity(intent)
    }

}