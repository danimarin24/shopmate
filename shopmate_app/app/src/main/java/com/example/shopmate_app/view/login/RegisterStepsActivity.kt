package com.example.shopmate_app.view.login

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.ActivityRegisterStepsBinding
import com.example.shopmate_app.view.login.ui.RegisterProfileFragment
import com.example.shopmate_app.view.login.ui.RegisterSecurityFragment

private const val ARG_USER_EMAIL = "userEmail"

class RegisterStepsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterStepsBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    private var fragment1Profile = RegisterProfileFragment()
    private var fragment2Security = RegisterSecurityFragment()
    private var fragment3Username = RegisterSecurityFragment()
    private var fragmentSteps = arrayListOf(fragment1Profile, fragment2Security, fragment3Username)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterStepsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // get extras arguments
        val userEmail = intent.getStringExtra(ARG_USER_EMAIL)

        // init fragment container to 0, register profile
        changeFragment(fragmentSteps[0], userEmail)


    }


    fun changeFragment(fragment: Fragment, userEmail : String?) {
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()

        var args = Bundle()
        if (userEmail != null) args.putString(ARG_USER_EMAIL, userEmail)
        fragment.arguments = args

        fragmentTransaction.replace(binding.fcvRegisterSteps.id, fragment)
        fragmentTransaction.commit()
    }
}