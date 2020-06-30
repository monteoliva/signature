package br.com.jadlog.signature.ui

import android.graphics.Bitmap
import android.util.Log
import java.io.ByteArrayOutputStream

class EncodeImage {
    fun encodeImage(bitmap: Bitmap?): ByteArray? {
        var retorno: ByteArray? = null
        if (bitmap == null) {
            return null
        }
        try {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            retorno = stream.toByteArray()
        } catch (e: Exception) {
            Log.d("Signature", "Error encodeImage - " + e.message)
        }

        // retorna em branco
        return retorno
    }
}