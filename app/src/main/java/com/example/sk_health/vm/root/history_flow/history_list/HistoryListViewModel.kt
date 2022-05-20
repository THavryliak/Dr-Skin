package com.example.sk_health.vm.root.history_flow.history_list

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

class HistoryListViewModel @Inject constructor(
    private val historyProvisor: IHistoryProvisor
) : ViewModel() {

    val historyItems: MutableLiveData<List<HistoryItemViewData>> by lazy { MutableLiveData<List<HistoryItemViewData>>() }

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            val appointmentsViewData = historyProvisor.getHistoryItems().map { mapToItemViewData(it) }
            historyItems.postValue(appointmentsViewData)
        }
    }

    private fun mapToItemViewData(history: HistoryItemLocal) = HistoryItemViewData(
        id = history.id,
        disease = HistoryDiseaseType.getByName(history.diseaseName),
        dateOfCreation = history.dateOfCreation,
        probability = history.probability
    )
}