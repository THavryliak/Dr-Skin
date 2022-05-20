package com.example.sk_health.domain.history

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class HistoryProvisor @Inject constructor(
    @NotNull private val realmConfig: RealmConfiguration
) : IHistoryProvisor {
    override suspend fun getHistoryItems(): List<HistoryItemLocal> {
        val realm = Realm.getInstance(realmConfig)
        val list = mutableListOf<HistoryItemLocal>()
        realm.executeTransactionAwait(Dispatchers.IO) {
            list.addAll(it.where<HistoryItemLocal>().findAll())
        }
        return list
    }

    override suspend fun addHistory(history: HistoryItemLocal) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAwait(Dispatchers.IO) {
            it.insertOrUpdate(history)
        }
    }

    override suspend fun deleteHistory(history: HistoryItemLocal) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAwait(Dispatchers.IO) {
            history.deleteFromRealm()
        }
    }

    override suspend fun getHistoryById(historyId: String): HistoryItemLocal? {
        val realm = Realm.getInstance(realmConfig)
        var appointment: HistoryItemLocal? = null
        realm.executeTransactionAwait(Dispatchers.IO) {
            appointment = it.where<HistoryItemLocal>().equalTo("id", historyId).findFirst()
        }
        return appointment
    }
}