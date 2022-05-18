package com.example.sk_health.domain

import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration

@Module
class RealmModule {

    @Provides
    fun provideRealmConfig(): RealmConfiguration = RealmConfiguration.Builder().schemaVersion(1L).build()
}