package com.example.sk_health.vm.root.appointment_flow.appointment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sk_health.domain.appointments.AppointmentItemLocal
import com.example.sk_health.domain.appointments.IAppointmentsProvisor
import com.example.sk_health.ui.main.root.appointment_flow.appointment.AppointmentViewData
import com.example.sk_health.vm.root.appointment_flow.AppointmentStatus
import com.example.sk_health.vm.root.appointment_flow.AppointmentVisitType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class AppointmentViewModel @Inject constructor(
    private val appointmentProvisor: IAppointmentsProvisor
) : ViewModel() {

    val appointment: MutableLiveData<AppointmentViewData> by lazy { MutableLiveData<AppointmentViewData>() }
    val validationError: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    private var isAddMode = true
    private var appointmentLocal: AppointmentItemLocal? = null

    fun init(appointmentId: String?) {
        if (!appointmentId.isNullOrBlank()) {
            isAddMode = false
            viewModelScope.launch(Dispatchers.IO) {
                appointmentLocal = appointmentProvisor.getAppointmentById(appointmentId)
                appointment.postValue(appointmentLocal?.let { matToAppointmentViewData(it) })
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAppointment(appointmentViewData: AppointmentViewData) {
        if (isFormValid(appointmentViewData)) {
            viewModelScope.launch(Dispatchers.IO) {
                val appointment = if (isAddMode) mapToAppointmentLocal(appointmentViewData) else updateExisting(appointmentViewData) ?: return@launch
                appointmentProvisor.addOrUpdateAppointment(appointment)
                validationError.postValue(true)
            }
        }
    }

    fun deleteAppointment() {
        viewModelScope.launch(Dispatchers.IO) {
            appointmentLocal?.let { appointmentProvisor.deleteAppointment(it) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun mapToAppointmentLocal(appointmentViewData: AppointmentViewData): AppointmentItemLocal {
        return AppointmentItemLocal().apply {
            id = UUID.randomUUID().toString()
            dateOfCreation = LocalDate.now().toString()
            appointmentStatus = appointmentViewData.statusType.status
            therapyType = appointmentViewData.therapyType.visitType
            dateOfAppointment = appointmentViewData.dateOfAppointment
            doctorType = appointmentViewData.doctorType.capitalize()
            doctor = appointmentViewData.doctor.capitalize()
            phone = appointmentViewData.phone
            address = appointmentViewData.address.capitalize()
            photoUrl = appointmentViewData.photoUrl
            disease = appointmentViewData.disease.capitalize()
            note = appointmentViewData.notes.capitalize()
        }
    }

    private fun updateExisting(appointmentViewData: AppointmentViewData): AppointmentItemLocal? {
        return appointmentLocal?.let {
            AppointmentItemLocal().apply {
                id = it.id
                dateOfCreation = it.dateOfCreation
                appointmentStatus = appointmentViewData.statusType.status
                therapyType = appointmentViewData.therapyType.visitType
                dateOfAppointment = appointmentViewData.dateOfAppointment
                doctor = appointmentViewData.doctor.capitalize()
                doctorType = appointmentViewData.doctorType.capitalize()
                phone = appointmentViewData.phone
                address = appointmentViewData.address.capitalize()
                photoUrl = appointmentViewData.photoUrl
                disease = appointmentViewData.disease.capitalize()
                note = appointmentViewData.notes.capitalize()
            }
        }
    }

    private fun matToAppointmentViewData(appointment: AppointmentItemLocal) = AppointmentViewData(
        doctor = appointment.doctor,
        doctorType = appointment.doctorType,
        therapyType = AppointmentVisitType.getByVisitType(appointment.therapyType),
        statusType = AppointmentStatus.getByStatus(appointment.appointmentStatus),
        address = appointment.address,
        phone = appointment.phone,
        disease = appointment.disease,
        dateOfAppointment = appointment.dateOfAppointment,
        photoUrl = appointment.photoUrl ?: "",
        notes = appointment.note ?: ""
    )

    private fun validateField(field: String): Boolean {
        return field.isNotBlank()
    }

    private fun isFormValid(appointmentViewData: AppointmentViewData): Boolean {
        val isDoctorValid = validateField(appointmentViewData.doctor)
        val isDoctorTypeValid = validateField(appointmentViewData.doctorType)
        val isDateOfAppointmentValid = validateField(appointmentViewData.dateOfAppointment)

        val isValid = (isDoctorValid && isDoctorTypeValid && isDateOfAppointmentValid)
        if (!isValid) validationError.postValue(false)

        return isValid
    }
}