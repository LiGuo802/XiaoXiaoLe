package cn.twoer.xiaoxiaole.box;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public abstract class BaseBox extends View {

    private static final String TAG = "BaseBox";

    public static int SIZE = -1;
    public static int NUM = 9;
    public static int ROUND = 10;
    public static int MARGIN = 20;

    public int mType;
    public boolean mCanMove;
    public boolean mCanRemove;

    public Paint mPaint;

    public BaseBox(int type, Context context) {
        this(type, context, null);
    }

    public BaseBox(int type, Context context, @Nullable AttributeSet attrs) {
        this(type, context, attrs, -1);
    }

    public BaseBox(int type, Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mType = type;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getColor());
    }

    public abstract int getColor();

    public void doSelectAnim() {

        float fromScale, toScale;

        if (getScaleX() == 1.0f) {
            fromScale = 1.0f;
            toScale = 1.15f;
        } else if (getScaleX() == 1.15f) {
            fromScale = 1.15f;
            toScale = 1.0f;
        } else {
            return;
        }

        ValueAnimator scaleAnim = ValueAnimator.ofFloat(fromScale, toScale);
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                setScaleX(scale);
                setScaleY(scale);
            }
        });
        scaleAnim.setDuration(100);
        scaleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnim.start();
    }

    public void update() {
        mPaint.setColor(getColor());
        invalidate();
    }
}
