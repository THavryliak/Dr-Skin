package com.example.sk_health.domain.appointments

import com.example.sk_health.vm.root.appointment_flow.AppointmentStatus
import com.example.sk_health.vm.root.appointment_flow.AppointmentVisitType
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*


open class Appointment : RealmObject() {
    @Required
    @PrimaryKey
    var id: String = ""
    var dateOfCreation: String = ""
    var dateOfAppointment: String = ""
    var doctor: String = ""
    var doctorType: String = ""
    var appointmentStatus: String = AppointmentStatus.NEW.status
    var therapyType: String = AppointmentVisitType.COMMON.visitType
    var phone: String = ""
    var address: String = ""
    var photoUrl: String? = null
    var disease: String = ""
    var note: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Appointment

        if (id != other.id) return false
        if (dateOfCreation != other.dateOfCreation) return false
        if (dateOfAppointment != other.dateOfAppointment) return false
        if (doctor != other.doctor) return false
        if (doctorType != other.doctorType) return false
        if (appointmentStatus != other.appointmentStatus) return false
        if (therapyType != other.therapyType) return false
        if (phone != other.phone) return false
        if (address != other.address) return false
        if (photoUrl != other.photoUrl) return false
        if (disease != other.disease) return false
        if (note == other.note) return false

        return true
    }

    override fun toString(): String {
        return "Appointment(" +
                "id=$id, " +
                "dateOfCreation=$dateOfCreation, " +
                "dateOfAppointment=$dateOfAppointment, " +
                "doctor=$doctor, " +
                "doctorType=$doctorType, " +
                "appointmentStatus=$appointmentStatus, " +
                "therapyType=$therapyType, " +
                "phone=$phone, " +
                "address=$address, " +
                "photoUrl=$photoUrl, " +
                "disease=$disease, " +
                "note=$note)"
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + dateOfCreation.hashCode()
        result = 31 * result + dateOfAppointment.hashCode()
        result = 31 * result + doctor.hashCode()
        result = 31 * result + (doctorType.hashCode())
        result = 31 * result + appointmentStatus.hashCode()
        result = 31 * result + (therapyType.hashCode())
        result = 31 * result + phone.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + (photoUrl?.hashCode() ?: 0)
        result = 31 * result + disease.hashCode()
        result = 31 * result + (note?.hashCode() ?: 0)
        return result
    }
}