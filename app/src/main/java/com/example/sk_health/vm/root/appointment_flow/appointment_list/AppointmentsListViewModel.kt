package com.example.sk_health.vm.root.appointment_flow.appointment_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sk_health.domain.appointments.Appointment
import com.example.sk_health.domain.appointments.IAppointmentsProvisor
import com.example.sk_health.vm.root.appointment_flow.AppointmentStatus
import com.example.sk_health.vm.root.appointment_flow.AppointmentVisitType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppointmentsListViewModel @Inject constructor(
    private val appointmentsProvisor: IAppointmentsProvisor
) : ViewModel() {

    val appointments: MutableLiveData<List<AppointmentItemViewData>> by lazy { MutableLiveData<List<AppointmentItemViewData>>() }

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            val appointmentsViewData = appointmentsProvisor.getAppointments().map { mapToItemViewData(it) }
            appointments.postValue(appointmentsViewData)
        }
    }

    private fun mapToItemViewData(appointment: Appointment) = AppointmentItemViewData(
        id = appointment.id,
        doctor = appointment.doctor,
        doctorType = appointment.doctorType,
        statusType = AppointmentStatus.getByStatus(appointment.appointmentStatus),
        therapyType = AppointmentVisitType.getByVisitType(appointment.therapyType),
        dateOfAppointment = appointment.dateOfAppointment,
        dateOfCreation = appointment.dateOfCreation,
        disease = appointment.disease,
    )
}