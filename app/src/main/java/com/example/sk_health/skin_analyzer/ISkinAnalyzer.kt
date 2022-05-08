package com.example.sk_health.skin_analyzer

import android.graphics.Bitmap

interface ISkinAnalyzer {
    fun analyze(photo: Bitmap): Pair<String, String>
}