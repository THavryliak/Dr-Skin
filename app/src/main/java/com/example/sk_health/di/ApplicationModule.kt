package com.example.sk_health.di

import android.content.Context
import com.example.sk_health.skin_analyzer.ISkinAnalyzer
import com.example.sk_health.skin_analyzer.SkinAnalyzer
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(val context: Context) {
    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideSkinAnalyzer(): ISkinAnalyzer = SkinAnalyzer(context)
}