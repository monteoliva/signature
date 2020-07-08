package br.com.jadlog.signature.ui

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat

import br.com.jadlog.signature.R

class AssinaturaComponent(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private lateinit var view: View
    private lateinit var assinaturaView: AssinaturaView
    private var listener: OnAssinaturaListener? = null

    init {
        Log.d("LIFECYCLE", "AssinaturaComponent - init")
        initViews(context, attrs)
    }

    private fun initViews(context: Context, attrs: AttributeSet) {
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        view = inflater.inflate(R.layout.assinatura, this)

        assinaturaView = view.findViewById(R.id.assinaturaView)

        view.findViewById<ImageView>(R.id.btnClose).setOnClickListener { hide() }
        view.findViewById<Button>(R.id.btnClear).setOnClickListener { assinaturaView.clear() }
        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            if (listener != null) {
                listener?.apply {
                    onBitmap(bitmap)
                    onByteArray(byteArray)
                    onHash(hash)
                }
            }
            hide()
        }
    }

    fun show() { view.visibility = View.VISIBLE }
    fun hide() { view.visibility = View.GONE    }

    val bitmap: Bitmap?
        get() = assinaturaView.bitmap

    val byteArray: ByteArray?
        get() = assinaturaView.byteArray

    val hash: String?
        get() = assinaturaView.hash

    fun setOnAssinaturaListener(listener: OnAssinaturaListener) {
        this.listener = listener
    }
}