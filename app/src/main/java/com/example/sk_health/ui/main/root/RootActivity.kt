package com.example.sk_health.ui.main.root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.sk_health.R
import com.example.sk_health.databinding.RootActivityBinding
import com.example.sk_health.ui.main.onboarding.RulesFragment

class RootActivity : AppCompatActivity() {

    lateinit var binding: RootActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.root_activity)

        val navController = (supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment).navController
        binding.bottomNavMenu.setupWithNavController(navController)
    }
}