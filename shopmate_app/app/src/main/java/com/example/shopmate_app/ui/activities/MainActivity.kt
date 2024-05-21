package com.example.shopmate_app.ui.activities

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.ActivityMainBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.ui.adapters.BoardAdapter
import com.example.shopmate_app.ui.adapters.ColorsChoseAdapter
import com.example.shopmate_app.ui.viewmodels.BoardViewModel
import com.example.shopmate_app.ui.viewmodels.ColorViewModel
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navListener: NavController.OnDestinationChangedListener
    private lateinit var context : Context

    private val mainViewModel: MainViewModel by viewModels()
    private val colorViewModel: ColorViewModel by viewModels()
    private val boardViewModel: BoardViewModel by viewModels()


    private var isBoardListEmpty = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = applicationContext

        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostMainFragmentContainer.id) as NavHostFragment
        navListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.homeFragment -> {
                    changeHeaderInfo("home")
                }
                R.id.searchFragment -> {
                    changeHeaderInfo("search")
                }
                R.id.templateFragment -> {
                    changeHeaderInfo("template")
                }
                R.id.profileFragment -> {
                    changeHeaderInfo("profile")
                }
                R.id.profileSettingFragment -> {
                    changeHeaderInfo("setting")
                }

            }
        }
        navController = navHostFragment.navController

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupObservers()

        // Fetch boards for a specific user
        boardViewModel.fetchBoards(mainViewModel.getUserId()!!)

        binding.btnCreateNew.setOnClickListener {
            boardViewModel.fetchBoards(mainViewModel.getUserId()!!)
            if (isBoardListEmpty) {
                Snackbar.make(binding.root, getString(R.string.errNoBoardFound), Snackbar.LENGTH_SHORT).show()
            } else {
                showCreateNewCard()
            }
        }

        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    changeHeaderInfo("home")
                    navController.navigate(R.id.homeFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.search -> {
                    changeHeaderInfo("search")
                    navController.navigate(R.id.searchFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.templates -> {
                    changeHeaderInfo("template")
                    navController.navigate(R.id.templateFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.profile -> {
                    changeHeaderInfo("profile")
                    navController.navigate(R.id.profileFragment)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        // INIT
        binding.bottomNavigationBar.menu.findItem(R.id.profile).setChecked(true)
        changeHeaderInfo("profile")
    }

    private fun setupObservers() {
        boardViewModel.boards.observe(this) { boards ->
            isBoardListEmpty = boards.isNullOrEmpty()
        }
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(navListener)
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(navListener)
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.navHostMainFragmentContainer)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun changeHeaderInfo(activeFragment: String) {
        binding.btnLeft.setImageResource(android.R.color.transparent)
        binding.btnLeft.setOnClickListener{}
        binding.btnRight.setImageResource(android.R.color.transparent)
        binding.btnRight.setOnClickListener{}

        when (activeFragment) {
            "home" -> {
                binding.txtHeaderTitle.text = getString(R.string.strHome)

                binding.btnLeft.setImageResource(R.drawable.application_edit_outline)
                binding.btnLeft.setOnClickListener {
                    binding.btnLeft.setImageResource(R.drawable.application_edit_outline)
                    //showCreateNewBoard()
                    // TODO: ('Add edit cards logic')
                }

                binding.btnRight.setImageResource(R.drawable.plus_circle_outline)
                binding.btnRight.setOnClickListener {
                    binding.btnRight.setImageResource(R.drawable.plus_circle_outline_open)
                    showCreateNewBoard()
                }

            }
            "search" -> {
                binding.txtHeaderTitle.text = getString(R.string.strSearch)
            }
            "template" -> {
                binding.txtHeaderTitle.text = getString(R.string.strTemplate)
            }
            "profile" -> {
                binding.txtHeaderTitle.text = getString(R.string.strProfile)

                binding.btnRight.setImageResource(R.drawable.menu)
                binding.btnRight.setOnClickListener {
                    binding.btnRight.setImageResource(R.drawable.menu_open)
                    showSettingsBottomDialog()
                }
            }
            "setting" -> {
                binding.txtHeaderTitle.text = getString(R.string.strProfileSettings)
            }
        }

    }

    private fun showSettingsBottomDialog() {
        val dialog = Dialog(navController.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_profile_dialog)

        val lytSettings = dialog.findViewById<LinearLayout>(R.id.lytSettings)
        val lytShare = dialog.findViewById<LinearLayout>(R.id.lytShare)
        val lytTheme = dialog.findViewById<LinearLayout>(R.id.lytTheme)

        lytSettings.setOnClickListener {
            dialog.dismiss()
            navController.navigate(R.id.action_profileFragment_to_profileSettingFragment)
            Toast.makeText(navController.context, "Settings", Toast.LENGTH_SHORT).show()
        }

        lytShare.setOnClickListener {
            dialog.dismiss()
            navController.navigate(R.id.shareProfileFragment)
            // Toast.makeText(navController.context, "Share", Toast.LENGTH_SHORT).show()
        }

        lytTheme.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(navController.context, "Theme", Toast.LENGTH_SHORT).show()
        }

        dialog.setOnDismissListener {
            binding.btnRight.setImageResource(R.drawable.menu)
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    fun showCreateNewCard() {
        val dialog = Dialog(navController.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_card_create_dialog)

        val txtCancel = dialog.findViewById<MaterialTextView>(R.id.txtCancel)
        val txtNext = dialog.findViewById<MaterialTextView>(R.id.txtNext)
        val etCardNameLyt = dialog.findViewById<TextInputLayout>(R.id.etCardNameLyt)
        val etCardName = dialog.findViewById<TextInputEditText>(R.id.etCardName)

        val rcvColors = dialog.findViewById<RecyclerView>(R.id.rcvDefaultColorsSelection)
        val cboBoardSelection = dialog.findViewById<Spinner>(R.id.cboBoardSelection)

        colorViewModel.getColors()

        colorViewModel.colorList.observe(this, Observer { colors ->
            rcvColors.adapter = ColorsChoseAdapter(colors)
            rcvColors.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        })

        /*
        boardViewModel.boardsEntity.observe(this, Observer { boardList ->
            if (!boardList.isNullOrEmpty()) {
                val titles = boardList.map { it.title }
                val adapter = ArrayAdapter(navController.context, R.layout.cbo_text_list, titles)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                cboBoardSelection.adapter = adapter
            }
        })
         */

        etCardNameLyt.setEndIconOnClickListener {
            etCardName.text?.clear()
        }

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }

        txtNext.setOnClickListener {
            dialog.dismiss()

            etCardName.text

            // acción de crear la lista
        }

        /*
        dialog.setOnDismissListener {
            binding.btnRight.setImageResource(R.drawable.menu)
        }
         */

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    fun showCreateNewBoard() {
        val dialog = Dialog(navController.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_board_create_dialog)

        val txtCancel = dialog.findViewById<MaterialTextView>(R.id.txtBoardCancel)
        val txtCreate = dialog.findViewById<MaterialTextView>(R.id.txtBoardCreate)
        val etBoardNameLyt = dialog.findViewById<TextInputLayout>(R.id.etBoardNameLyt)
        val etBoardName = dialog.findViewById<TextInputEditText>(R.id.etBoardName)


        etBoardNameLyt.setEndIconOnClickListener {
            etBoardName.text?.clear()
        }

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }

        txtCreate.setOnClickListener {
            val title = etBoardName.text.toString()
            if (title.isNotEmpty()) {
                val newBoard = BoardEntity(0, etBoardName.text.toString(), mainViewModel.getUserId()!!)
                boardViewModel.addBoard(newBoard)
                boardViewModel.fetchBoards(mainViewModel.getUserId()!!)
                dialog.dismiss()
            } else {
                Snackbar.make(binding.root, getString(R.string.errNameInvalid), Snackbar.LENGTH_SHORT).show()
            }

        }


        dialog.setOnDismissListener {
            binding.btnRight.setImageResource(R.drawable.plus_circle_outline)
        }


        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}