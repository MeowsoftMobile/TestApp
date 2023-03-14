package com.meowsoft.testapp.presentation.main

import android.Manifest
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.meowsoft.testapp.R
import com.meowsoft.testapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var permissionRequester: ActivityResultLauncher<Array<String>>

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionRequester = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {}

        permissionRequester
            .launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_content_nav
        ) as NavHostFragment
        val navController = navHostFragment.navController
    }
}
