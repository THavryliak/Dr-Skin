package com.example.sk_health.domain.appointments

interface IAppointmentsProvisor {
    suspend fun getAppointments(): List<Appointment>
    suspend fun addOrUpdateAppointment(appointment: Appointment)
    suspend fun deleteAppointment(appointment: Appointment)
    suspend fun getAppointmentById(appointmentId: String): Appointment?
}