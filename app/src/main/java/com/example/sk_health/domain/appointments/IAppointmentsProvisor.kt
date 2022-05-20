package com.example.sk_health.domain.appointments

interface IAppointmentsProvisor {
    suspend fun getAppointments(): List<AppointmentItemLocal>
    suspend fun addOrUpdateAppointment(appointment: AppointmentItemLocal)
    suspend fun deleteAppointment(appointment: AppointmentItemLocal)
    suspend fun getAppointmentById(appointmentId: String): AppointmentItemLocal?
}