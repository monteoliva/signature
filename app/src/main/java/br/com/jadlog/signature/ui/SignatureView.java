package br.com.jadlog.signature.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import br.com.jadlog.signature.R;

public class SignatureView extends View {
    // set the stroke width
    private static final float STROKE_WIDTH = 3f;
    private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;

    private Paint paint = new Paint();
    private Path path   = new Path();

    private float lastTouchX;
    private float lastTouchY;
    private final RectF dirtyRect = new RectF();

    private int withSize   = 0;
    private int heightSize = 0;

    private EncodeImage encodeImage = new EncodeImage();

    /**
     * Constructor
     *
     * @param context
     */
    public SignatureView(Context context)                               { super(context);        init(); }
    public SignatureView(Context context, @Nullable AttributeSet attrs) { super(context, attrs); init(); }

    private void init() {
      int color = ContextCompat.getColor(getContext(), R.color.colorLine);

      paint.setAntiAlias(true);
      paint.setColor(color);
      paint.setStyle(Paint.Style.STROKE);
      paint.setStrokeJoin(Paint.Join.ROUND);
      paint.setStrokeWidth(STROKE_WIDTH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        withSize   = w;
        heightSize = h;
    }

    public byte[] getHash() {
        Bitmap bmp = getBitmap();

        return (bmp != null) ? encodeImage.encodeImage(bmp) : null;
    }

    private Bitmap getBitmap() {
        Bitmap signatureBitmap = Bitmap.createBitmap(withSize, heightSize, Bitmap.Config.ARGB_8888);

        this.draw(new Canvas(signatureBitmap));

        Bitmap emptyBitmap = Bitmap.createBitmap(withSize, heightSize, signatureBitmap.getConfig());

        if (signatureBitmap.sameAs(emptyBitmap)) { return null; }

        return signatureBitmap;
    }

    /**
     * clear signature canvas
     */
    public void clear() {
        path.reset();
        this.invalidate();
    }

    // all touch events during the drawing
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(this.path, this.paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = eventX;
                lastTouchY = eventY;

                path.moveTo(eventX, eventY);
                path.lineTo(eventX, eventY);

                return true;

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:
                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);

                    expandDirtyRect(historicalX, historicalY);
                    path.lineTo(historicalX, historicalY);
                }
                path.lineTo(eventX, eventY);
                break;

            default:
                return false;
        }

        invalidate((int) (dirtyRect.left   - HALF_STROKE_WIDTH),
                   (int) (dirtyRect.top    - HALF_STROKE_WIDTH),
                   (int) (dirtyRect.right  + HALF_STROKE_WIDTH),
                   (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

        lastTouchX = eventX;
        lastTouchY = eventY;

        return true;
    }

    private void expandDirtyRect(float historicalX, float historicalY) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX;
        }
        else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX;
        }

        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY;
        }
        else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY;
        }
    }

    private void resetDirtyRect(float eventX, float eventY) {
        dirtyRect.left   = Math.min(lastTouchX, eventX);
        dirtyRect.right  = Math.max(lastTouchX, eventX);
        dirtyRect.top    = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }
}