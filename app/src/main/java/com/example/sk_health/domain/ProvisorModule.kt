package com.example.sk_health.domain

import com.example.sk_health.domain.appointments.AppointmentsProvisor
import com.example.sk_health.domain.appointments.IAppointmentsProvisor
import com.example.sk_health.domain.history.HistoryProvisor
import com.example.sk_health.domain.history.IHistoryProvisor
import dagger.Binds
import dagger.Module

@Module
interface ProvisorModule {

    @Binds
    fun bindAppointmentsProvisor(provisor: AppointmentsProvisor): IAppointmentsProvisor

    @Binds
    fun bindHistoryProvisor(provisor: HistoryProvisor): IHistoryProvisor
}