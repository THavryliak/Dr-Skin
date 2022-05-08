package com.example.sk_health.di.vm_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sk_health.skin_analyzer.ISkinAnalyzer
import com.example.sk_health.vm.root.home.HomeViewModel

class HomeViewModelFactory(
    private val skinAnalyzer: ISkinAnalyzer
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(skinAnalyzer) as T
    }
}