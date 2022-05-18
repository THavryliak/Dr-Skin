package com.example.sk_health.di.app

import android.app.Application
import com.example.sk_health.di.ApplicationComponent
import com.example.sk_health.di.ApplicationModule
import com.example.sk_health.di.DaggerApplicationComponent
import io.realm.Realm

class App : Application() {

    lateinit var appComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}