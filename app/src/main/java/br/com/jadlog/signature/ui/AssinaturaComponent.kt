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
import androidx.lifecycle.ViewModelProvider

import br.com.jadlog.signature.R

class AssinaturaComponent(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private lateinit var view: View
    private lateinit var drawView: DrawView
    private lateinit var layoutComponent: LinearLayout
    private var listener: OnAssinaturaListener? = null

    private val acitivity: AppCompatActivity = context as AppCompatActivity
    private val viewModel = ViewModelProvider(acitivity).get(AssinaturaComponentViewModel::class.java)

    init {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))

        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        view = inflater.inflate(R.layout.assinatura_component, this)

        layoutComponent = view.findViewById(R.id.layoutComponent)
        drawView = view.findViewById(R.id.drawView)

        view.findViewById<ImageView>(R.id.btnClose).setOnClickListener { hide() }
        view.findViewById<AppCompatButton>(R.id.btnClear).setOnClickListener {
            drawView.clear()
        }
        view.findViewById<AppCompatButton>(R.id.btnSave).setOnClickListener {
            if (listener != null) {
                listener?.apply {
                    onBitmap(bitmap)
                    onByteArray(byteArray)
                    onHash(hash)
                }
            }
        }
    }

    private fun initViewModel() {
        val orientation: Int = acitivity.resources.configuration.orientation
        viewModel.setViewLiveData(layoutComponent)
        viewModel.setOrientationLiveData(orientation)
    }

    fun show() {
        viewModel.show()
        if (viewModel.orientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            acitivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }
    fun hide() {
        viewModel.hide()
        acitivity.requestedOrientation = viewModel.orientation
    }

    val bitmap: Bitmap?       get() = drawView.bitmap
    val byteArray: ByteArray? get() = drawView.byteArray
    val hash: String?         get() = drawView.hash

    fun setOnAssinaturaListener(listener: OnAssinaturaListener) {
        this.listener = listener
    }
}