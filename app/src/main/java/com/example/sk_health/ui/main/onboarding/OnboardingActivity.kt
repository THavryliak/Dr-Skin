package com.example.sk_health.ui.main.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.sk_health.R
import com.example.sk_health.databinding.OnboardingActivityBinding

class OnboardingActivity : AppCompatActivity() {

    lateinit var binding: OnboardingActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.onboarding_activity)
    }
}