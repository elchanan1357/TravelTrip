package com.example.traveltrip

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val navHostController: NavHostFragment? =
//            supportFragmentManager.findFragmentById(R.id.main_nav_host) as? NavHostFragment
//
//        if (navHostController == null) {
//            Log.e("NavigationError", "navHostController is NULL!")
//        }
//
//        val navController = navHostController?.navController
//
//        if (navController == null) {
//            Log.e("NavigationError", "navController is NULL!")
//        }
//
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_bar)
//
//        Log.d("NavigationError","create")
//        navController?.let {
//            NavigationUI.setupWithNavController(bottomNavigationView, it)
//            Log.d("NavigationError","create 1")
//            try {
////                NavigationUI.setupActionBarWithNavController(this, navController!!)
//            } catch (e: Exception) {
//                Log.e("NavigationError", "Error in setupActionBarWithNavController: ${e.message}")
//            }
//
////            NavigationUI.setupActionBarWithNavController(this, it)
//        }
//
//        Log.d("NavigationError","finish")
//    }


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
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {

                    true
                }

                R.id.menu_diary -> {

                    true
                }

                R.id.menu_discover -> {

                    true
                }

                R.id.menu_blogs -> {

                    true
                }

                R.id.menu_profile -> {

                    true
                }

                else -> false
            }
        }


    }

}