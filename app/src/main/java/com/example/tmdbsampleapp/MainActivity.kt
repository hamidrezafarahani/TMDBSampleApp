package com.example.tmdbsampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }
}