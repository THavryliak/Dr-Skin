package com.example.sk_health.di.vm_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sk_health.vm.root.appointment_flow.appointment.AppointmentViewModel
import com.example.sk_health.vm.root.appointment_flow.appointment_list.AppointmentsListViewModel
import com.example.sk_health.vm.root.home.HomeViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    homeViewModelProvider: Provider<HomeViewModel>,
    appointmentsListViewModelProvider: Provider<AppointmentsListViewModel>,
    appointmentViewModelProvider: Provider<AppointmentViewModel>
): ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        HomeViewModel::class.java to homeViewModelProvider,
        AppointmentsListViewModel::class.java to appointmentsListViewModelProvider,
        AppointmentViewModel::class.java to appointmentViewModelProvider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}