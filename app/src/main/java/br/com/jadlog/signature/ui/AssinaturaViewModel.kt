package br.com.jadlog.signature.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AssinaturaViewModel : ViewModel() {
    val assinaturaView: MutableLiveData<AssinaturaView> by lazy {
        MutableLiveData<AssinaturaView>()
    }
}