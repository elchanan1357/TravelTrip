package com.example.traveltrip

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
//    private lateinit var bottomNav: BottomNavigationView
//    private lateinit var navHostFragments: Map<Int, NavHostFragment>

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


//        bottomNav = findViewById(R.id.bottom_bar)
//
//        navHostFragments = mapOf(
//            R.id.homePageFragment to supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment,
//            R.id.myTripsFragment to supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment,
//            R.id.discoverFragment to supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment,
//            R.id.postsFragment to supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment,
//            R.id.profileFragment to supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
//        )

//
//        //hide the nav button
//        navHostFragments.forEach { (id, fragment) ->
//            supportFragmentManager.beginTransaction()
//                .hide(fragment)
//                .commit()
//        }
//
//        // Show the Home fragment by default
//        supportFragmentManager.beginTransaction()
//            .show(navHostFragments[R.id.homePageFragment]!!)
//            .commit()

//        bottomNav.setOnItemSelectedListener { item ->
//            // Hide all fragments first
//            navHostFragments.forEach { (_, fragment) ->
//                supportFragmentManager.beginTransaction()
//                    .hide(fragment)
//                    .commit()
//            }
//
//            // Show the selected fragment
//            val selectedFragment = navHostFragments[item.itemId]
//            selectedFragment?.let {
//                supportFragmentManager.beginTransaction()
//                    .show(it)
//                    .commit()
//            }
//
//            true
//        }
    }
}