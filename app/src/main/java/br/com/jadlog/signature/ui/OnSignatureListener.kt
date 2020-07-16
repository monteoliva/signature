package br.com.jadlog.signature.ui

import android.graphics.Bitmap

interface OnSignatureListener {
    fun onBitmap(bitmap: Bitmap?)
    fun onByteArray(byteArray: ByteArray?)
    fun onHash(hash: String?)
}