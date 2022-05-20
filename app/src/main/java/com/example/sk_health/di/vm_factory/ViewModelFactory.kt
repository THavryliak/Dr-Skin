package com.example.sk_health.di.vm_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sk_health.vm.root.appointment_flow.appointment.AppointmentViewModel
import com.example.sk_health.vm.root.appointment_flow.appointment_list.AppointmentsListViewModel
import com.example.sk_health.vm.root.history_flow.history.HistoryItemViewModel
import com.example.sk_health.vm.root.history_flow.history_list.HistoryListViewModel
import com.example.sk_health.vm.root.home.HomeViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    homeViewModelProvider: Provider<HomeViewModel>,
    appointmentsListViewModelProvider: Provider<AppointmentsListViewModel>,
    appointmentViewModelProvider: Provider<AppointmentViewModel>,
    historyListViewModelProvider: Provider<HistoryListViewModel>,
    historyItemViewModelProvider: Provider<HistoryItemViewModel>
): ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        HomeViewModel::class.java to homeViewModelProvider,
        AppointmentsListViewModel::class.java to appointmentsListViewModelProvider,
        AppointmentViewModel::class.java to appointmentViewModelProvider,
        HistoryListViewModel::class.java to historyListViewModelProvider,
        HistoryItemViewModel::class.java to historyItemViewModelProvider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}