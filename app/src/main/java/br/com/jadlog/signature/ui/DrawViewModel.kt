package br.com.jadlog.signature.ui

import android.graphics.Path
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class DrawViewModel : ViewModel() {
    private val pathLiveData: MutableLiveData<Path> by lazy {
        MutableLiveData<Path>()
    }

    fun setPathLiveData(path: Path) {
        if (pathLiveData.value == null) {
            pathLiveData.postValue(path)
        }
    }

    val path: Path get() = pathLiveData.value!!
}