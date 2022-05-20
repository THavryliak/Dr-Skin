package com.example.sk_health.vm.root.history_flow

import com.example.sk_health.R

enum class HistoryDiseaseType(val disease: String, val color: String, val resId: Int) {
    NORMAL("Normal", "#00FA21", R.drawable.healthy_skin),
    ACNE("Acne", "#FFE500", R.drawable.acne),
    HAIR_LOSS("Hair Loss", "#FAAE52", R.drawable.hair_loss),
    NAIL_FUNGUS("Nail Fungus", "#FF5722", R.drawable.nail_fungus),
    SKIN_ALLERGY("Skin Allergy", "#fe2712", R.drawable.skin_allergy);

    companion object {
        fun getByName(disease: String) = values().firstOrNull { it.disease == disease } ?: NORMAL
    }
}