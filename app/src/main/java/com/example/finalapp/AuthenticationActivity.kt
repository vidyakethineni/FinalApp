package com.example.finalapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.finalapp.databinding.ActivityAuthenticationBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
/**
 * The main activity for user authentication, handling the login and signup process.
 */
class AuthenticationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_authentication)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    /**
     * Called when the activity is becoming visible to the user.
     */
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
    /**
     * Handle navigate up action when the user presses the Up button in the action bar.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_authentication)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}