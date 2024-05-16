package com.example.shopmate_app.ui.fragments.login

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.shopmate_app.R
import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.utils.PasswordUtils
import com.example.shopmate_app.databinding.FragmentLoginBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.UserEntity
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.UserViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.time.LocalDate
import java.util.UUID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    private lateinit var context : Context

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        context = requireContext()

        binding.btnLogin.setOnClickListener {
            signEmailIn()
        }

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
            .setServerClientId(AppConstants.GOOGLE_ID_TOKEN)
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        var googleUser : UserEntity? = null
        var googleName : String = ""
        var googleUsernameGenerated : String? = null
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


                userViewModel.getGoogleSub(googleIdTokenCredential.idToken)

                var googleTokenSub : String? = null
                userViewModel.googleSub.observe(viewLifecycleOwner, Observer {googleSub ->
                    googleTokenSub = googleSub
                    if (googleTokenSub != null) {
                        googleName = googleIdTokenCredential.displayName.toString()
                        googleEmail = googleIdTokenCredential.id
                        googlePhoneNumber = googleIdTokenCredential.phoneNumber
                        googleProfileImage = googleIdTokenCredential.profilePictureUri.toString()
                        googleSubHashed = PasswordUtils.hashString(googleTokenSub!!)

                        // Aquí puedes realizar las operaciones que dependen de los datos obtenidos de Google
                        userViewModel.getUserByGoogleSub(googleSubHashed)
                        userViewModel.userEntity.observe(viewLifecycleOwner) { user ->
                            googleUser = user

                            if (googleUser == null) {
                                // error no existe un usuario, hay que crearlo

                                // generar username
                                userViewModel.getUserByGoogleSub(googleSubHashed)
                                userViewModel.usernameGenerated.observe(viewLifecycleOwner) { usernameGenerated ->
                                    googleUsernameGenerated = usernameGenerated

                                    // user has setting and stat id set to null, because in the api, we will create automatically a new setting and a new stat.
                                    val user : UserEntity = UserEntity(null, googleUsernameGenerated!!, googleName, null,
                                        googleEmail, googlePhoneNumber, googleProfileImage, googleSubHashed, null,
                                        LocalDate.now().toString(), LocalDate.now().toString(), 0, 0)

                                    var userInserted: UserEntity? = null
                                    userViewModel.addUser(user)
                                    userViewModel.userEntity.observe(viewLifecycleOwner) { user ->
                                        userInserted = user
                                    }
                                    Log.i("userInserted", userInserted.toString())

                                    mainViewModel.saveUserId(userInserted!!.userId!!)

                                    findNavController().navigate(R.id.action_loginFragment_to_mainActivity)

                                }



                            } else {
                                mainViewModel.saveUserId(googleUser!!.userId!!)

                                findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                            }
                        }
                    }
                })

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


    private fun signEmailIn() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        if (username.isEmpty()) {
            //err es empty
            return
        }

        userViewModel.getUserByUsername(username)

        var userEntity : UserEntity? = null
        userViewModel.userEntity.observe(viewLifecycleOwner, Observer {user ->
            userEntity = user

            if (userEntity == null) {
                //err no existe ningun user
                Log.e("ERROR", "ES NULL")
                Toast.makeText(context, "no existe ningun user", Toast.LENGTH_SHORT).show()
            }
            if (PasswordUtils.checkPasswordHash(password, userEntity!!.password!!)) {
                // la contraseña coincide, inciciar sesión
                mainViewModel.saveUserId(userEntity!!.userId!!)
                findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
            }
        })
    }
}