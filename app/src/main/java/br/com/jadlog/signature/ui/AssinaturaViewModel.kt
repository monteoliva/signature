package br.com.jadlog.signature.ui

import android.graphics.Path
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AssinaturaViewModel : ViewModel() {
    private val pathLiveData: MutableLiveData<Path> by lazy {
        MutableLiveData<Path>()
    }

    fun setPathLiveData(path: Path) {
        if (pathLiveData.value == null) {
            Log.d("COMPONENT", "new pathLiveData")
            pathLiveData.postValue(path)
        }
    }

    val path: Path? get() = pathLiveData.value!!
}