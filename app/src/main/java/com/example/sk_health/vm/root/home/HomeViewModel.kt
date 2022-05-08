package com.example.sk_health.vm.root.home

import android.media.Image
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sk_health.R
import com.example.sk_health.skin_analyzer.ISkinAnalyzer
import com.example.sk_health.utils.ImageUtil.toBitmap
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val skinAnalyzer: ISkinAnalyzer
) : ViewModel() {

    val photoResult: MutableLiveData<DiseaseViewData> by lazy { MutableLiveData<DiseaseViewData>() }

    fun processPhoto(image: Image) {
        val output = skinAnalyzer.analyze(image.toBitmap())
        photoResult.postValue(getDiseaseInfo(label = output.first, probability = output.second))
    }

    private fun getDiseaseInfo(label: String, probability: String): DiseaseViewData {
        return when(label) {
            DiseaseLabels.ACNE.diseaseLabel -> DiseaseViewData(title = label, probability = probability, imageRes = R.drawable.acne, textRes = R.string.acne_info)
            DiseaseLabels.NORMAL.diseaseLabel -> DiseaseViewData(title = label, probability = probability, imageRes = R.drawable.healthy_skin, textRes = R.string.normal_skin_info)
            DiseaseLabels.SKIN_ALLERGY.diseaseLabel -> DiseaseViewData(title = label, probability = probability, imageRes = R.drawable.skin_allergy, textRes = R.string.skin_allergy_info)
            DiseaseLabels.NAIL_FUNGUS.diseaseLabel -> DiseaseViewData(title = label, probability = probability, imageRes = R.drawable.nail_fungus, textRes = R.string.nail_fungus_info)
            DiseaseLabels.HAIR_LOSS.diseaseLabel -> DiseaseViewData(title = label, probability = probability, imageRes = R.drawable.hair_loss, textRes = R.string.hair_loss_info)
            else -> throw UnsupportedOperationException()
        }
    }
}