package com.example.finalapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.finalapp.databinding.ActivityMainBinding
import com.example.finalapp.databinding.NavHeaderMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
/**
 * The main activity representing the application's user interface.
 *
 * This activity includes the navigation drawer, toolbar setup, and handles user interactions
 * within the app.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_recent_orders, R.id.nav_calendar
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val navBinding: NavHeaderMainBinding = NavHeaderMainBinding.inflate(layoutInflater)
        val headerView: View = navBinding.root
        binding.navView.addHeaderView(headerView)
        mainViewModel.loggedInUser.observe(this) {
            it?.apply {
                navBinding.user = this
            }
        }

    }

    override fun onStart() {
        super.onStart()
        // Check for location permissions
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission already granted, proceed to fetch location

        } else {
            // Request location permissions
            val LOCATION_PERMISSION_REQUEST_CODE = 100
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun onSignOutClicked(view: View) {
        Firebase.auth.signOut()
        startActivity(Intent(this, AuthenticationActivity::class.java))
        finish()
    }
}