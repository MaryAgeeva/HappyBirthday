package com.mary.happybirthday.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.mary.happybirthday.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)
        adjustNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.main_frame).navigateUp() || super.onSupportNavigateUp()
    }

    private fun adjustNavigation() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navController = findNavController(R.id.main_frame)
        setupActionBarWithNavController(navController, AppBarConfiguration(navController.graph))

        setUIVisibility()
    }

    private fun setUIVisibility() {
        findNavController(R.id.main_frame).addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.detailFragment -> supportActionBar?.show()
                R.id.birthdayFragment -> supportActionBar?.hide()
            }
        }
    }
}
