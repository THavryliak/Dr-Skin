package com.example.sk_health.vm.root.appointment_flow.appointment_list

import com.example.sk_health.vm.root.appointment_flow.AppointmentStatus
import com.example.sk_health.vm.root.appointment_flow.AppointmentVisitType

data class AppointmentItemViewData(
    val id: String,
    val doctor: String,
    val doctorType: String,
    val therapyType: AppointmentVisitType,
    val statusType: AppointmentStatus,
    val dateOfCreation: String,
    val dateOfAppointment: String,
    val disease: String
)