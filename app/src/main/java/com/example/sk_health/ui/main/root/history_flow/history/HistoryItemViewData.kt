package com.example.sk_health.ui.main.root.history_flow.history

import com.example.sk_health.vm.root.history_flow.HistoryDiseaseType

data class HistoryItemViewData(
    val id: String?,
    val disease: HistoryDiseaseType,
    val dateOfCreation: String,
    val probability: Double
)