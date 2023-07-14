package com.example.tmdbsampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tmdbsampleapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 *   66-92-110-530-118-128
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Using view binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.bottomNavigation

        val navController = findNavController(R.id.fragmentContainerView)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.moviesFragment, R.id.searchFragment)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}