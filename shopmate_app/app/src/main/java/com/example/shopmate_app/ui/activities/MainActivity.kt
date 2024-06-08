package com.example.shopmate_app.ui.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.databinding.ActivityMainBinding
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ColorEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.ValidateShareLinkRequestEntity
import com.example.shopmate_app.ui.adapters.ColorsChoseAdapter
import com.example.shopmate_app.ui.viewmodels.BoardViewModel
import com.example.shopmate_app.ui.viewmodels.CardViewModel
import com.example.shopmate_app.ui.viewmodels.ColorViewModel
import com.example.shopmate_app.ui.viewmodels.MainViewModel
import com.example.shopmate_app.ui.viewmodels.ShareViewModel
import com.example.shopmate_app.ui.viewmodels.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var gestureDetector: GestureDetectorCompat
    private lateinit var navListener: NavController.OnDestinationChangedListener
    private lateinit var context : Context

    private val mainViewModel: MainViewModel by viewModels()
    private val shareViewModel: ShareViewModel by viewModels()
    private val colorViewModel: ColorViewModel by viewModels()
    private val boardViewModel: BoardViewModel by viewModels()
    private val cardViewModel : CardViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by viewModels()


    private var isBoardListEmpty = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = applicationContext

        val intent = intent
        if (Intent.ACTION_VIEW == intent.action) {
            val uri: Uri? = intent.data
            uri?.let {
                val token = it.lastPathSegment
                val validateTokenRequest = ValidateShareLinkRequestEntity(mainViewModel.getUserId()!!, token!!)
                token.let {
                    shareViewModel.validateCardShareLinkToken(validateTokenRequest)
                    observeViewModel(validateTokenRequest)
                }
            }
        }

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
                R.id.shareProfileFragment -> {
                    changeHeaderInfo("shareProfile")
                }
                R.id.cardDetailsViewFragment -> {
                    changeHeaderInfo("cardDetailsView")
                }
                R.id.cardCategorySelectItemsFragment -> {
                    changeHeaderInfo("cardCategorySelectItems")
                }
                R.id.profileStatsDetailsFragment -> {
                    changeHeaderInfo("profileStatsDetails")
                }

            }
        }
        navController = navHostFragment.navController
        gestureDetector = GestureDetectorCompat(this, SwipeGestureListener(navController))

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
                    // navController.get
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

    private inner class SwipeGestureListener(val navController: NavController) : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (e1 == null || e2 == null) return false

            val diffX = e2.x - e1.x
            val diffY = e2.y - e1.y
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight()
                    }
                    return true
                }
            }
            return false
        }

        private fun onSwipeRight() {
            if (navController.currentBackStackEntry != null && navController.previousBackStackEntry != null) {
                navController.popBackStack()
            }
        }
    }

    private fun observeViewModel(validateTokenRequest: ValidateShareLinkRequestEntity) {
        shareViewModel.tokenValidation.observe(this, Observer { isValid ->
            if (isValid) {
                Toast.makeText(this, "Token valido", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Token inválido", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupObservers() {
        cardViewModel.cards.observe(this) { _ ->
            sharedViewModel.fetchBoards(mainViewModel.getUserId()!!)
        }

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
        binding.lytCoordinatorMenu.visibility = View.VISIBLE
        binding.lytHeader.visibility = View.VISIBLE
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

            "profile" -> {
                binding.txtHeaderTitle.text = getString(R.string.strProfile)

                binding.btnRight.setImageResource(R.drawable.menu)
                binding.btnRight.setOnClickListener {
                    binding.btnRight.setImageResource(R.drawable.menu_open)
                    showSettingsBottomDialog()
                }
            }

            "cardDetailsView" -> {
                binding.lytHeader.visibility = View.GONE
                binding.lytCoordinatorMenu.visibility = View.GONE
            }

            "cardCategorySelectItems" -> {
                binding.lytHeader.visibility = View.GONE
                binding.lytCoordinatorMenu.visibility = View.GONE
            }

            "profileStatsDetails" -> {
                binding.lytHeader.visibility = View.GONE
            }

            "setting" -> {
                binding.txtHeaderTitle.text = getString(R.string.strProfileSettings)
            }
            "shareProfile" -> {
                binding.btnLeft.setImageResource(R.drawable.close)
                binding.btnLeft.setOnClickListener {
                    navController.popBackStack()
                }
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


        boardViewModel.fetchBoards(mainViewModel.getUserId()!!)


        boardViewModel.boards.observe(this, Observer { boardList ->
            if (!boardList.isNullOrEmpty()) {
                // Crear un ArrayAdapter que almacene BoardEntity
                val adapter = object : ArrayAdapter<BoardEntity>(
                    navController.context,
                    R.layout.cbo_text_list,
                    boardList
                ) {
                    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                        val view = super.getView(position, convertView, parent)
                        (view as TextView).text = getItem(position)?.title
                        return view
                    }

                    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                        val view = super.getDropDownView(position, convertView, parent)
                        (view as TextView).text = getItem(position)?.title
                        return view
                    }
                }
                adapter.setDropDownViewResource(R.layout.cbo_text_list)
                cboBoardSelection.adapter = adapter
            }
        })



        etCardNameLyt.setEndIconOnClickListener {
            etCardName.text?.clear()
        }

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }

        txtNext.setOnClickListener {
            val selectedColor = (rcvColors.adapter as ColorsChoseAdapter).getSelectedColor()
            if (selectedColor == null) {
                Snackbar.make(binding.root, "Selecciona un color", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val selectedBoard = cboBoardSelection.selectedItem as BoardEntity
            val selectedBoardId = selectedBoard.boardId // Obtener el ID del BoardEntity seleccionado
            val currentId = mainViewModel.getUserId()!!
            val newCard = CardEntity(
                cardId = 0, // Asumimos que el ID será generado por el backend
                cardName = etCardName.text.toString(),
                ownerId = currentId,
                isPublic = 1,
                isTemplate = 0,
                isArchived = 0,
                estimatedPrice = null,
                color = selectedColor,
            )
            cardViewModel.addCardToABoard(selectedBoardId, newCard)
            dialog.dismiss()
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