package com.example.sk_health.skin_analyzer

import android.content.Context
import android.graphics.Bitmap
import com.example.sk_health.ml.SkinModel
import org.tensorflow.lite.support.image.TensorImage
import javax.inject.Inject

class SkinAnalyzer @Inject constructor(
    context: Context
) : ISkinAnalyzer {

    private val model = SkinModel.newInstance(context)

    override fun analyze(photo: Bitmap): Pair<String, String> {
        val image = TensorImage.fromBitmap(photo)
        val output = model.process(image).probabilityAsCategoryList.maxByOrNull { it.score }!!
        return Pair(output.label, output.score.toString())
    }
}