package com.example.traveltrip

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var activeNavController: NavController
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navHostFragments: Map<Int, NavHostFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_bar)
        navHostFragments = mapOf(
            R.id.getStarted to supportFragmentManager.findFragmentById(R.id.starting_host) as NavHostFragment,
            R.id.discoverFragment to supportFragmentManager.findFragmentById(R.id.trips_host) as NavHostFragment,
            R.id.postsFragment to supportFragmentManager.findFragmentById(R.id.posts_host) as NavHostFragment,
            R.id.profileFragment to supportFragmentManager.findFragmentById(R.id.profile_host) as NavHostFragment,
            R.id.homePageFragment to supportFragmentManager.findFragmentById(R.id.home_host) as NavHostFragment
        )

        hideNavHost()
        displayNavHost(R.id.getStarted)
        settingButtonBar()

        onBackPressedDispatcher.addCallback(this) {
            handleBackPressed()
        }
    }


    private fun handleBackPressed() {
        val navController = activeNavController

        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
            return
        }

        val isAtRootTab = bottomNav.selectedItemId == R.id.getStarted

        if (!isAtRootTab) {
            switchToNavHostFragment(R.id.homePageFragment)
        }

//        val previous = getPreviousNavHostFragmentWithBackStack()
//        if (previous != null) {
//            switchToNavHostFragment(previous.first)
//            return
//        }

        else {
            finish()
        }
    }


    private fun hideNavHost() {
        navHostFragments.forEach { (_, fragment) ->
            supportFragmentManager.beginTransaction()
                .hide(fragment)
                .commit()
        }
    }

    private fun displayNavHost(itemId: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        navHostFragments[itemId]?.let {
            transaction.show(it)
            activeNavController = it.navController
            it.navController.popBackStack(
                it.navController.graph.startDestinationId,
                false
            )
            transaction.commit()
        }
    }

//    override fun onBackPressed() {
//        if (activeNavController.popBackStack()) {
//            return
//        }
//
//        val previous = getPreviousNavHostFragmentWithBackStack()
//        if (previous != null) {
//            switchToNavHostFragment(previous.first)
//            return
//        }
//
//        super.onBackPressed()
//    }
//

    fun switchToNavHostFragment(itemId: Int) {
        hideNavHost()
        displayNavHost(itemId)

        updateBottomBarState(itemId)
        bottomNav.selectedItemId = itemId
    }


    private fun getPreviousNavHostFragmentWithBackStack(): Pair<Int, NavHostFragment>? {
        val candidates = listOf(
            R.id.getStarted to supportFragmentManager.findFragmentById(R.id.starting_host) as NavHostFragment,
            R.id.homePageFragment to supportFragmentManager.findFragmentById(R.id.home_host) as NavHostFragment,
            R.id.postsFragment to supportFragmentManager.findFragmentById(R.id.posts_host) as NavHostFragment,
            R.id.discoverFragment to supportFragmentManager.findFragmentById(R.id.trips_host) as NavHostFragment,
            R.id.profileFragment to supportFragmentManager.findFragmentById(R.id.profile_host) as NavHostFragment,
        )

        return candidates.firstOrNull { it.second.navController.previousBackStackEntry != null }
    }


    private fun settingButtonBar() {
        bottomNav.setOnItemSelectedListener { item ->
            hideNavHost()
            displayNavHost(item.itemId)
            updateBottomBarState(item.itemId)
            true
        }
    }


    private fun updateBottomBarState(itemId: Int) {
        when (itemId) {
            R.id.homePageFragment -> {
                bottomNav.menu.findItem(R.id.homePageFragment).isVisible = false
                bottomNav.menu.findItem(R.id.discoverFragment).isVisible = false
                bottomNav.visibility = View.VISIBLE
            }

            R.id.profileFragment,
            R.id.discoverFragment,
            R.id.postsFragment -> {
                bottomNav.menu.findItem(R.id.homePageFragment).isVisible = true
                bottomNav.menu.findItem(R.id.discoverFragment).isVisible = true
                bottomNav.visibility = View.VISIBLE
            }

            R.id.getStarted -> {
                bottomNav.visibility = View.GONE
            }

        }
    }


}