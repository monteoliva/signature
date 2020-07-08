package br.com.jadlog.signature.ui

import android.graphics.Bitmap

interface OnAssinaturaListener {
    fun onBitmap(bitmap: Bitmap?)
    fun onByteArray(byteArray: ByteArray?)
    fun onHash(hash: String?)
}