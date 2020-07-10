package br.com.jadlog.signature.ui

import android.util.Log
import android.widget.PopupWindow
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AssinaturaViewModel : ViewModel() {
    private val popupLiveData: MutableLiveData<PopupWindow> by lazy {
        MutableLiveData<PopupWindow>()
    }

    fun setPopupLiveData(popupWindow: PopupWindow) {
        if (popupLiveData.value == null) {
            Log.d("COMPONENT", "new popupLiveData")
            popupLiveData.postValue(popupWindow)
        }
    }

    val popupWindow: PopupWindow? get() = popupLiveData.value!!
}