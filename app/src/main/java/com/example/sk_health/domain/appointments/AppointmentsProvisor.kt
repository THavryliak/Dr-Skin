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

    override suspend fun getAppointments(): List<AppointmentItemLocal> {
        val realm = Realm.getInstance(realmConfig)
        val list = mutableListOf<AppointmentItemLocal>()
        realm.executeTransactionAwait(Dispatchers.IO) {
            list.addAll(it.where<AppointmentItemLocal>().findAll())
        }
        return list
    }

    override suspend fun addOrUpdateAppointment(appointment: AppointmentItemLocal) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAwait(Dispatchers.IO) {
            it.insertOrUpdate(appointment)
        }
    }

    override suspend fun deleteAppointment(appointment: AppointmentItemLocal) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAwait(Dispatchers.IO) {
            appointment.deleteFromRealm()
        }
    }

    override suspend fun getAppointmentById(appointmentId: String): AppointmentItemLocal? {
        val realm = Realm.getInstance(realmConfig)
        var appointment: AppointmentItemLocal? = null
        realm.executeTransactionAwait(Dispatchers.IO) {
            appointment = it.where<AppointmentItemLocal>().equalTo("id", appointmentId).findFirst()
        }
        return appointment
    }
}