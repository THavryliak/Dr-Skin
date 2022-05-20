package com.example.sk_health.utils

import android.graphics.*
import android.media.Image
import java.io.ByteArrayOutputStream

object ImageUtil {

    fun Image.toBitmap(): Bitmap {
        val yBuffer = this.planes[0].buffer // Y

        val ySize = yBuffer.remaining()

        val nv21 = ByteArray(ySize)

        yBuffer.get(nv21, 0, ySize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}