package br.com.jadlog.signature.ui

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

import br.com.jadlog.signature.R

class AssinaturaComponent(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private lateinit var view: View
    private lateinit var assinaturaView: DrawView
    private var listener: OnAssinaturaListener? = null

    private val acitivity: AppCompatActivity = context as AppCompatActivity

    init {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))

        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        view = inflater.inflate(R.layout.assinatura_component, this)

        assinaturaView = view.findViewById(R.id.assinaturaView)

        view.findViewById<ImageView>(R.id.btnClose).setOnClickListener { hide() }
        view.findViewById<AppCompatButton>(R.id.btnClear).setOnClickListener {
            assinaturaView.clear()
        }
        view.findViewById<AppCompatButton>(R.id.btnSave).setOnClickListener {
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

    private fun initViewModel() {}

    fun show() {
        acitivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        view.visibility = View.VISIBLE
    }
    fun hide() { view.visibility = View.GONE }

    val bitmap: Bitmap?       get() = assinaturaView.bitmap
    val byteArray: ByteArray? get() = assinaturaView.byteArray
    val hash: String?         get() = assinaturaView.hash

    fun setOnAssinaturaListener(listener: OnAssinaturaListener) {
        this.listener = listener
    }
}