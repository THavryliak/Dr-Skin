package com.example.sk_health.domain

import com.example.sk_health.domain.appointments.AppointmentsProvisor
import com.example.sk_health.domain.appointments.IAppointmentsProvisor
import dagger.Binds
import dagger.Module

@Module
interface ProvisorModule {

    @Binds
    fun bindAppointmentsProvisor(provisor: AppointmentsProvisor): IAppointmentsProvisor
}