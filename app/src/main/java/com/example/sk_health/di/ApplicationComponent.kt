package com.example.sk_health.di

import com.example.sk_health.ui.main.root.home.HomeFragment
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(fragment: HomeFragment)
}