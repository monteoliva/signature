package br.com.jadlog.signature.ui

import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class SignatureComponentViewModel : ViewModel() {
    private val hideLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val viewLiveData: MutableLiveData<LinearLayout> by lazy {
        MutableLiveData<LinearLayout>()
    }

    private val orientationLiveData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun setViewLiveData(view: LinearLayout) {
        if (viewLiveData.value == null) {
            view.visibility = View.GONE
            viewLiveData.postValue(view)
            hideLiveData.postValue(true)
        }
        else {
            if (hideLiveData.value != null && hideLiveData.value!!) {
                view.visibility = View.GONE
            }
        }
    }

    fun setOrientationLiveData(orientation: Int) {
        if (orientationLiveData.value == null) {
            orientationLiveData.postValue(orientation)
        }
    }

    fun show() {
        hideLiveData.postValue(false)
        viewLiveData.value!!.visibility = View.VISIBLE
    }
    fun hide() {
        hideLiveData.postValue(true)
        viewLiveData.value!!.visibility = View.GONE
    }

    val orientation: Int get() = orientationLiveData.value!!
}