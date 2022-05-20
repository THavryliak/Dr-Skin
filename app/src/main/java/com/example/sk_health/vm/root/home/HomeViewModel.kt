package com.example.sk_health.vm.root.home

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sk_health.R
import com.example.sk_health.domain.history.HistoryItemLocal
import com.example.sk_health.domain.history.IHistoryProvisor
import com.example.sk_health.skin_analyzer.ISkinAnalyzer
import com.example.sk_health.ui.main.root.history_flow.history.HistoryItemViewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val skinAnalyzer: ISkinAnalyzer,
    private val historyProvisor: IHistoryProvisor
) : ViewModel() {

    val photoResult: MutableLiveData<DiseaseViewData> by lazy { MutableLiveData<DiseaseViewData>() }

    fun processPhoto(image: Bitmap) {
        val output = skinAnalyzer.analyze(image)
        photoResult.postValue(getDiseaseInfo(label = output.first, probability = output.second))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveResultToHistory(historyViewData: HistoryItemViewData) {
        viewModelScope.launch(Dispatchers.IO) {
            val historyLocal = mapToHistoryLocal(historyViewData)
            historyProvisor.addHistory(historyLocal)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun mapToHistoryLocal(historyViewData: HistoryItemViewData): HistoryItemLocal {
        return HistoryItemLocal().apply {
            id = UUID.randomUUID().toString()
            dateOfCreation = LocalDate.now().toString()
            diseaseName = historyViewData.disease.disease
            probability = historyViewData.probability
        }
    }

    private fun getDiseaseInfo(label: String, probability: String): DiseaseViewData {
        return when(label) {
            DiseaseLabels.ACNE.diseaseLabel -> DiseaseViewData(title = label, probability = probability, imageRes = R.drawable.acne, textRes = R.string.acne_info)
            DiseaseLabels.NORMAL.diseaseLabel -> DiseaseViewData(title = label, probability = probability, imageRes = R.drawable.healthy_skin, textRes = R.string.normal_skin_info)
            DiseaseLabels.SKIN_ALLERGY.diseaseLabel -> DiseaseViewData(title = label, probability = probability, imageRes = R.drawable.skin_allergy, textRes = R.string.skin_allergy_info)
            DiseaseLabels.NAIL_FUNGUS.diseaseLabel -> DiseaseViewData(title = label, probability = probability, imageRes = R.drawable.nail_fungus, textRes = R.string.nail_fungus_info)
            DiseaseLabels.HAIR_LOSS.diseaseLabel -> DiseaseViewData(title = "Hair Loss", probability = probability, imageRes = R.drawable.hair_loss, textRes = R.string.hair_loss_info)
            else -> throw UnsupportedOperationException()
        }
    }
}