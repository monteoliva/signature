package br.com.jadlog.signature.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

internal class DrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint: Paint = Paint()
    private var lastTouchX: Float = 0f
    private var lastTouchY: Float = 0f
    private val dirtyRect: RectF = RectF()
    private var withSize: Int = 0
    private var heightSize:Int = 0

    private val acitivity: AppCompatActivity = context as AppCompatActivity
    private val colorLocal: Int = ContextCompat.getColor(context, android.R.color.black)
    private val viewModel = ViewModelProvider(acitivity).get(DrawViewModel::class.java)

    companion object {
        private const val STROKE_WIDTH = 20f
        private const val HALF_STROKE_WIDTH = STROKE_WIDTH / 2
    }

    init {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        paint.apply {
            isAntiAlias = true
            color       = colorLocal
            style       = Paint.Style.STROKE
            strokeJoin  = Paint.Join.ROUND
            strokeWidth = STROKE_WIDTH
        }
    }

    fun initViewModel() {
        viewModel.setPathLiveData(Path())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        withSize   = w
        heightSize = h
    }

    val byteArray: ByteArray?
        get() {
            val bmp: Bitmap? = bitmap
            return if (bmp != null) { EncodeImage.encodeImage(bmp) } else { null }
        }

    val hash: String?
        get() {
            val bytes: ByteArray? = byteArray
            return if (bytes == null) { null } else { EncodeImage.encodeImage(bytes) }
        }

    val bitmap: Bitmap?
        get() {
            val signatureBitmap = Bitmap.createBitmap(withSize, heightSize, Bitmap.Config.ARGB_8888)
            draw(Canvas(signatureBitmap))
            val emptyBitmap = Bitmap.createBitmap(withSize, heightSize, signatureBitmap.config)

            return if (signatureBitmap.sameAs(emptyBitmap)) { null } else { signatureBitmap }
        }

    /**
     * clear signature canvas
     */
    fun clear() {
        viewModel.path.reset()
        this.postInvalidate()
    }

    // all touch events during the drawing
    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(viewModel.path, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val eventX = event.x
        val eventY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = eventX
                lastTouchY = eventY
                viewModel.path.moveTo(eventX, eventY)
                viewModel.path.lineTo(eventX, eventY)
                return true
            }
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                resetDirtyRect(eventX, eventY)
                val historySize = event.historySize
                var i = 0
                while (i < historySize) {
                    val historicalX = event.getHistoricalX(i)
                    val historicalY = event.getHistoricalY(i)
                    expandDirtyRect(historicalX, historicalY)
                    viewModel.path.lineTo(historicalX, historicalY)
                    i++
                }
                viewModel.path.lineTo(eventX, eventY)
            }
            else -> return false
        }

        postInvalidate((dirtyRect.left   - HALF_STROKE_WIDTH).toInt(),
                       (dirtyRect.top    - HALF_STROKE_WIDTH).toInt(),
                       (dirtyRect.right  + HALF_STROKE_WIDTH).toInt(),
                       (dirtyRect.bottom + HALF_STROKE_WIDTH).toInt())

        lastTouchX = eventX
        lastTouchY = eventY
        return true
    }

    private fun expandDirtyRect(historicalX: Float, historicalY: Float) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX
        }
        else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX
        }
        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY
        }
        else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY
        }
    }

    private fun resetDirtyRect(eventX: Float, eventY: Float) {
        dirtyRect.left   = Math.min(lastTouchX, eventX)
        dirtyRect.right  = Math.max(lastTouchX, eventX)
        dirtyRect.top    = Math.min(lastTouchY, eventY)
        dirtyRect.bottom = Math.max(lastTouchY, eventY)
    }
}