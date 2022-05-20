package com.example.sk_health.vm.root.appointment_flow

enum class AppointmentVisitType(val visitType: String, val color: String) {
    COMMON("Common", "#26D5EC"),
    THERAPY("Therapy", "#FF5722"),
    CONSULTATION("Consultation", "#9C27B0"),
    CHILDREN("Children", "#F32163");

    companion object {
        fun getByVisitType(visitType: String) = values().firstOrNull{ it.visitType == visitType } ?: COMMON
    }
}