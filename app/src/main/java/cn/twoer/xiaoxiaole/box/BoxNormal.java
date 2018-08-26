package cn.twoer.xiaoxiaole.box;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

public class BoxNormal extends BaseBox {

    public BoxNormal(int type, Context context) {
        super(type, context);
    }

    @Override
    public int getColor() {
        switch (mType) {
            case BoxIndex.BASE_RED:
                return Color.parseColor("#ea9999");
            case BoxIndex.BASE_BLUE:
                return Color.parseColor("#0099ff");
            case BoxIndex.BASE_GREEN:
                return Color.parseColor("#66cc33");
            case BoxIndex.BASE_ORANGE:
                return Color.parseColor("#cc99ff");
            case BoxIndex.BASE_YELLOW:
                return Color.parseColor("#ffff99");
            case 444:
                return Color.BLACK;
            case 555:
                return Color.MAGENTA;
            case 666:
                return Color.CYAN;
        }
        return Color.WHITE;
    }

    public static int getRandomType() {
        return getRandomType(BoxIndex.BASE_RED,
                BoxIndex.BASE_BLUE,
                BoxIndex.BASE_GREEN,
                BoxIndex.BASE_ORANGE,
                BoxIndex.BASE_YELLOW);
    }

    public static int getRandomType(int... args) {
        return args[(int) (Math.random() * args.length)];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(SIZE, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(SIZE, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0, 0, SIZE, SIZE, ROUND, ROUND, mPaint);
    }
}
