package com.example.traveltrip

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController =
            (supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment).navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_bar)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment || destination.id == R.id.registerFragment || destination.id == R.id.getStarted) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }

            val menu = bottomNavigationView.menu
            if (destination.id == R.id.homePageFragment) {
                menu.findItem(R.id.homePageFragment).isVisible = false
//                menu.findItem(R.id.myTripsFragment).isVisible = false
                menu.findItem(R.id.discoverFragment).isVisible = false
            } else {
                menu.findItem(R.id.homePageFragment).isVisible = true
                menu.findItem(R.id.myTripsFragment).isVisible = true
                menu.findItem(R.id.discoverFragment).isVisible = true
            }
        }


    }
}