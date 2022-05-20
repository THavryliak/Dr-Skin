package com.example.sk_health.vm.root.history_flow.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sk_health.domain.history.HistoryItemLocal
import com.example.sk_health.domain.history.IHistoryProvisor
import com.example.sk_health.ui.main.root.history_flow.history.HistoryItemViewData
import com.example.sk_health.vm.root.history_flow.HistoryDiseaseType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryItemViewModel @Inject constructor(
    private val historyProvisor: IHistoryProvisor
) : ViewModel() {

    val history: MutableLiveData<HistoryItemViewData> by lazy { MutableLiveData<HistoryItemViewData>() }

    private var historyItem: HistoryItemLocal? = null

    fun init(historyId: String?) {
        if (!historyId.isNullOrBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                historyItem = historyProvisor.getHistoryById(historyId)
                historyItem?.let { history.postValue(mapToViewData(it)) }
            }
        }
    }

    fun deleteHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            historyItem?.let { historyProvisor.deleteHistory(it) }
        }
    }

    private fun mapToViewData(history: HistoryItemLocal): HistoryItemViewData = HistoryItemViewData(
        id = null,
        dateOfCreation = history.dateOfCreation,
        disease = HistoryDiseaseType.getByName(history.diseaseName),
        probability = history.probability
    )
}