package com.example.sk_health.ui.main.root.appointment_flow.appointment

import com.example.sk_health.vm.root.appointment_flow.AppointmentStatus
import com.example.sk_health.vm.root.appointment_flow.AppointmentVisitType

data class AppointmentViewData(
    val doctor: String,
    val doctorType: String,
    val therapyType: AppointmentVisitType,
    val statusType: AppointmentStatus,
    val address: String,
    val phone: String,
    val disease: String,
    val dateOfAppointment: String,
    val photoUrl: String,
    val notes: String
)