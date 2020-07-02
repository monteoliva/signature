package br.com.jadlog.signature.ui

import java.io.ByteArrayOutputStream

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat

class AssinaturaView : View {
    private val paint: Paint = Paint()
    private val path: Path = Path()
    private var lastTouchX: Float = 0f
    private var lastTouchY: Float = 0f
    private val dirtyRect: RectF = RectF()
    private var withSize: Int = 0
    private var heightSize:Int = 0

    companion object {
        private const val STROKE_WIDTH = 20f
        private const val HALF_STROKE_WIDTH = STROKE_WIDTH / 2
    }

    /**
     * Constructor
     *
     * @param context
     */
    constructor(context: Context?) : super(context) { init() }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init() }

    private fun init() {
        val color = ContextCompat.getColor(context, android.R.color.black)

        paint.isAntiAlias = true
        paint.color       = color
        paint.style       = Paint.Style.STROKE
        paint.strokeJoin  = Paint.Join.ROUND
        paint.strokeWidth = STROKE_WIDTH
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        withSize  = w
        heightSize = h
    }

    val hash: ByteArray?
        get() {
            val bmp = bitmap
            return if (bmp != null) { encodeImage(bmp) } else { null }
        }

    private val bitmap: Bitmap?
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
        path.reset()
        this.postInvalidate()
    }

    // all touch events during the drawing
    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val eventX = event.x
        val eventY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = eventX
                lastTouchY = eventY
                path.moveTo(eventX, eventY)
                path.lineTo(eventX, eventY)
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
                    path.lineTo(historicalX, historicalY)
                    i++
                }
                path.lineTo(eventX, eventY)
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

    @SuppressLint("WrongThread")
    private fun encodeImage(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) { return null }

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }
}