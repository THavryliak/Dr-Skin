package com.example.sk_health.domain.appointments

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class AppointmentsProvisor @Inject constructor(
    @NotNull private val realmConfig: RealmConfiguration
) : IAppointmentsProvisor {

    override suspend fun getAppointments(): List<Appointment> {
        val realm = Realm.getInstance(realmConfig)
        val list = mutableListOf<Appointment>()
        realm.executeTransactionAwait(Dispatchers.IO) {
            list.addAll(it.where<Appointment>().findAll())
        }
        return list
    }

    override suspend fun addOrUpdateAppointment(appointment: Appointment) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAwait(Dispatchers.IO) {
            it.insertOrUpdate(appointment)
        }
    }

    override suspend fun deleteAppointment(appointment: Appointment) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAwait(Dispatchers.IO) {
            appointment.deleteFromRealm()
        }
    }

    override suspend fun getAppointmentById(appointmentId: String): Appointment? {
        val realm = Realm.getInstance(realmConfig)
        var appointment: Appointment? = null
        realm.executeTransactionAwait(Dispatchers.IO) {
            appointment = it.where<Appointment>().equalTo("id", appointmentId).findFirst()
        }
        return appointment
    }
}