package ipca.edjd.idomtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TemperatureView extends View {


    private float value = 0;
    private float width ;
    private float height ;
    private final float TERM_WIDTH = 80.f;

    public TemperatureView(Context context) {
        super(context);
        init();
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //width = getLayoutParams().width;
        //height = getLayoutParams().height;

        Paint paint= new Paint();
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

        //RectF rectF = new RectF(width/2.f-TERM_WIDTH/2.f + ,0, TERM_WIDTH,height);

        RectF rectF = new RectF(width/2.f - TERM_WIDTH ,0, TERM_WIDTH + width/2.f,height);

        canvas.drawRect(rectF, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        RectF rectFValue = new RectF(width/2.f - TERM_WIDTH ,value, TERM_WIDTH + width/2.f,height);

        canvas.drawRect(rectFValue, paint);

    }

    public float getValue() {
        return value = height + (height/value);
    }

    public void setValue(float value) {
        this.value = height - (height * value);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension((int)width, (int)height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                value = y;
                invalidate();
                break;


        }

        return true;
    }
}
