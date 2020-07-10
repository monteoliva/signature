package br.com.jadlog.signature.ui

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import br.com.jadlog.signature.R

class Assinatura private constructor(context: Context) {
    private lateinit var popupView: View
    private lateinit var popupWindow: PopupWindow
    private lateinit var viewModel: AssinaturaViewModel

    private val activity: AppCompatActivity = context as AppCompatActivity

    init {
        initViews()
        initViewModel()
    }

    fun getInstance(context: Context?): Assinatura? {
        return Assinatura(context!!)
    }

    private fun initViews() {
        viewModel = ViewModelProvider(activity).get(AssinaturaViewModel::class.java)

        popupView = LayoutInflater.from(activity).inflate(R.layout.assinatura_component, null)
        popupView.findViewById<ImageView>(R.id.btnClose).setOnClickListener { hide() }
        popupView.findViewById<Button>(R.id.btnClear).setOnClickListener {  }
        popupView.findViewById<Button>(R.id.btnSave).setOnClickListener {
            hide()
        }

        val width: Int  = LinearLayout.LayoutParams.MATCH_PARENT
        val height: Int = LinearLayout.LayoutParams.MATCH_PARENT

        popupWindow = PopupWindow(popupView, width, height, true)
    }

    private fun initViewModel() {
        viewModel.setPopupLiveData(popupWindow)
    }

    fun show() {
        if (!viewModel.popupWindow!!.isShowing) {
            viewModel.popupWindow!!.showAtLocation(popupView, Gravity.TOP, 0, 0)
        }
    }

    fun hide() { viewModel.popupWindow!!.dismiss() }
}