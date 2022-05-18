package com.example.sk_health.vm.root.appointment_flow

enum class AppointmentStatus(val status: String, val color: String) {
    REJECTED("Rejected", "#CF3B2C"),
    ACCEPTED("Accepted", "#45B64A"),
    NEW("New", "#26D5EC");

    companion object {
        fun getByStatus(status: String) = values().firstOrNull { it.status == status } ?: NEW
    }
}