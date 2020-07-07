package br.com.jadlog.signature.ui

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity

import br.com.jadlog.signature.R

class AssinaturaComponent private constructor(private val context: Context,
                                              private val listener: OnAssinaturaListener) {
    private lateinit var popupView: View
    private lateinit var assinaturaView: AssinaturaView
    private lateinit var popupWindow: PopupWindow

    private val activity: AppCompatActivity = context as AppCompatActivity

    init {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        popupView = LayoutInflater.from(activity).inflate(R.layout.assinatura, null)

        assinaturaView = popupView.findViewById(R.id.assinaturaView)

        popupView.findViewById<ImageView>(R.id.btnClose).setOnClickListener { hide() }
        popupView.findViewById<Button>(R.id.btnClear).setOnClickListener { clear() }
        popupView.findViewById<Button>(R.id.btnSave).setOnClickListener {
            listener.onSuccess(assinaturaView.hash!!)
            hide()
        }

        val width: Int  = LinearLayout.LayoutParams.MATCH_PARENT
        val height: Int = LinearLayout.LayoutParams.MATCH_PARENT

        popupWindow = PopupWindow(popupView, width, height, true)
    }

    private fun initViewModel() {

    }

    fun show() {
        if (!popupWindow.isShowing) {
            activity.runOnUiThread(Thread(
                    Runnable { popupWindow.showAtLocation(popupView, Gravity.TOP, 0, 0) }
            ))
        }
    }

    fun hide() { popupWindow.dismiss() }

    fun clear() { assinaturaView.clear() }

    /**************************************************************************************
     * Builder (Design Patterns)
     */
    class Builder {
        private lateinit var context: Context
        private lateinit var listener: OnAssinaturaListener

        fun context(context: Context): Builder {
            this.context = context
            return this
        }

        fun listener(listener: OnAssinaturaListener): Builder {
            this.listener = listener
            return this
        }

        fun build(): AssinaturaComponent {
            return AssinaturaComponent(context, listener)
        }
    }
}