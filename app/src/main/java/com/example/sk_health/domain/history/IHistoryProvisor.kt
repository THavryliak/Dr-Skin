package com.example.sk_health.domain.history

interface IHistoryProvisor {
    suspend fun getHistoryItems(): List<HistoryItemLocal>
    suspend fun addHistory(history: HistoryItemLocal)
    suspend fun deleteHistory(history: HistoryItemLocal)
    suspend fun getHistoryById(historyId: String): HistoryItemLocal?
}