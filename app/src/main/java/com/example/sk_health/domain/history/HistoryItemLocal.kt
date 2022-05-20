package com.example.sk_health.domain.history

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class HistoryItemLocal : RealmObject() {
    @PrimaryKey
    @Required
    var id: String = ""
    var dateOfCreation = ""
    var diseaseName: String = ""
    var probability: Double = 0.0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HistoryItemLocal

        if (id != other.id) return false
        if (dateOfCreation != other.dateOfCreation) return false
        if (diseaseName != other.diseaseName) return false
        if (probability != other.probability) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + dateOfCreation.hashCode()
        result = 31 * result + diseaseName.hashCode()
        result = 31 * result + probability.hashCode()
        return result
    }

    override fun toString(): String {
        return "HistoryItemLocal(" +
                "id=$id, " +
                "dateOfCreation=$dateOfCreation, " +
                "diseaseName=$diseaseName, " +
                "probability=$probability)"
    }
}