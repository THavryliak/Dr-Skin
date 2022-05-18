package com.example.sk_health.ui.main.root

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.sk_health.R
import com.example.sk_health.databinding.RootActivityBinding
import com.example.sk_health.di.app.App

class RootActivity : AppCompatActivity() {

    lateinit var binding: RootActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.root_activity)

        (applicationContext as App).appComponent.inject(this)

        val navController = (supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment).navController
        binding.bottomNavMenu.setupWithNavController(navController)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = mutableListOf (Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray()
    }
}