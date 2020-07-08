package br.com.jadlog.signature.ui

import java.io.ByteArrayOutputStream

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

object EncodeImage {
    fun encodeImage(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) { return null }

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }

    fun encodeImage(bytes: ByteArray?) : String? {
        if (bytes == null) { return null }
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    fun decodeImageBase64(input: String?) : Bitmap? {
        if (input == null) { return null }
        val decodeBytes = Base64.decode(input, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodeBytes, 0, decodeBytes.size)
    }
}