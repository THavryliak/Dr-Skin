package com.example.sk_health.di

import com.example.sk_health.di.vm_factory.ViewModelFactory
import com.example.sk_health.domain.ProvisorModule
import com.example.sk_health.domain.RealmModule
import com.example.sk_health.ui.main.root.RootActivity
import dagger.Component

@Component(modules = [ApplicationModule::class, ProvisorModule::class, RealmModule::class])
interface ApplicationComponent {
    fun inject(activity: RootActivity)
    fun viewModelsFactory(): ViewModelFactory
}