package br.com.jadlog.signature.ui

interface OnAssinaturaListener {
    fun onSuccess(hash: ByteArray)
}