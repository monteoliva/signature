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

class SignatureComponent(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private lateinit var view: View
    private lateinit var drawView: DrawView
    private lateinit var layoutComponent: LinearLayout
    private var listener: OnSignatureListener? = null

    private val activity: AppCompatActivity = context as AppCompatActivity
    private val viewModel = ViewModelProvider(activity).get(SignatureComponentViewModel::class.java)

    init {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))

        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        view = inflater.inflate(R.layout.assinatura_component, this)
        view.apply {
            drawView        = findViewById(R.id.drawView)
            layoutComponent = findViewById(R.id.layoutComponent)

            findViewById<ImageView>(R.id.btnClose).setOnClickListener { hide() }
            findViewById<AppCompatButton>(R.id.btnClear).setOnClickListener {
                drawView.clear()
            }
            findViewById<AppCompatButton>(R.id.btnSave).setOnClickListener {
                if (listener != null) {
                    listener?.apply {
                        onBitmap(bitmap)
                        onByteArray(byteArray)
                        onHash(hash)
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        val orientation: Int = activity.resources.configuration.orientation
        viewModel.apply {
            setViewLiveData(layoutComponent)
            setOrientationLiveData(orientation)
        }
    }

    fun show() {
        viewModel.show()
        if (viewModel.orientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }
    fun hide() {
        viewModel.hide()
        activity.requestedOrientation = viewModel.orientation
    }

    val bitmap: Bitmap?       get() = drawView.bitmap
    val byteArray: ByteArray? get() = drawView.byteArray
    val hash: String?         get() = drawView.hash

    fun setOnAssinaturaListener(listener: OnSignatureListener) {
        this.listener = listener
    }
}