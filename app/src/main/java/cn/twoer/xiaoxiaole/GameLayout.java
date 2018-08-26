package cn.twoer.xiaoxiaole;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import cn.twoer.xiaoxiaole.algorithm.Algo;
import cn.twoer.xiaoxiaole.box.BaseBox;

public class GameLayout extends FrameLayout {

    private static final String TAG = "GameLayout";

    private Context mContext;
    private BaseBox[][] mBoxes;

    private int mLastSelectX = -1;
    private int mLastSelectY = -1;

    public GameLayout(@NonNull Context context) {
        this(context, null);
    }

    public GameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public GameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void setData(BaseBox[][] boxes) {
        mBoxes = boxes;

        for (int i = 0; i < mBoxes.length; i++) {
            for (int j = 0; j < mBoxes[0].length; j++) {
                addView(mBoxes[i][j], new FrameLayout.LayoutParams(BaseBox.SIZE, BaseBox.SIZE));
                mBoxes[i][j].setX(i * (BaseBox.MARGIN + BaseBox.SIZE) + BaseBox.MARGIN);
                mBoxes[i][j].setY(j * (BaseBox.MARGIN + BaseBox.SIZE) + BaseBox.MARGIN);
            }
        }
        //Algo.self(mBoxes, new int[mBoxes.length][mBoxes[0].length]);
        //
    }

    private int mLastDownX;
    private int mLastDownY;

    private int getLocation(float pos) {
        return (int) (pos / (BaseBox.SIZE + BaseBox.MARGIN));
    }

    private void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleActionDown(event);
                break;

            case MotionEvent.ACTION_MOVE:
                handleActionMove(event);
                break;

            case MotionEvent.ACTION_UP:
                handleActionUp(event);
                break;
            default:
                break;
        }
        return true;
    }

    private void handleActionMove(MotionEvent event) {

    }


    private void handleActionDown(MotionEvent event) {
        mLastDownX = getLocation(event.getX());
        mLastDownY = getLocation(event.getY());
    }

    private void handleActionUp(MotionEvent event) {
        int x = getLocation(event.getX());
        int y = getLocation(event.getY());

        if (x == mLastDownX && y == mLastDownY) {
            // 点击事件
            if (mLastSelectX == -1 && mLastSelectY == -1) {
                // 第一次点击，高亮当前元素
                mLastSelectX = x;
                mLastSelectY = y;
                mBoxes[x][y].doSelectAnim();
            } else {
                // 第二次点击
                if (x == mLastSelectX && y == mLastSelectY) {
                    // 第二次点击相同元素，取消高亮效果
                    mBoxes[mLastSelectX][mLastSelectY].doSelectAnim();
                    mLastSelectX = -1;
                    mLastSelectY = -1;
                } else if ((x == mLastSelectX && Math.abs(y - mLastSelectY) == 1)
                        || (y == mLastSelectY && Math.abs(x - mLastSelectX) == 1)) {
                    // 第二次点击相邻元素，交换
                    swap(false, mLastSelectX, mLastSelectY, x, y);
                } else {
                    // 高亮新的，取消之前高亮的
                    mBoxes[mLastSelectX][mLastSelectY].doSelectAnim();
                    mLastSelectX = x;
                    mLastSelectY = y;
                    mBoxes[x][y].doSelectAnim();
                }
            }
            return;
        }

        if (x == mLastDownX) {
            if (y < mLastDownY) { // up
            } else if (y > mLastDownY) { // down
            }
        } else if (y == mLastDownY) { // left
            if (x < mLastDownX) {
            } else if (x > mLastDownX) { // right
            }
        }
    }

    private void swap(final boolean forceFinish, final int x1, final int y1, final int x2, final int y2) {
        mLastSelectX = -1;
        mLastSelectY = -1;


        Animator animSwap = AnimUtils.getSwapAnim(mBoxes[x1][y1], mBoxes[x2][y2], x1, y1, x2, y2);

        animSwap.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                BaseBox temp = mBoxes[x1][y1];
                mBoxes[x1][y1] = mBoxes[x2][y2];
                mBoxes[x2][y2] = temp;

                if (!forceFinish) {
                    if (Algo.swapSucceed(mBoxes, x1, y1, x2, y2, new int[mBoxes.length][mBoxes[0].length])) {
                        updateState();
                    } else {
                        swap(true, x1, y1, x2, y2);
                    }
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mBoxes[x1][y1].setScaleX(1f);
                mBoxes[x1][y1].setScaleY(1f);
            }
        });

        animSwap.start();
    }

    private void updateState() {
        for (BaseBox[] mBoxe : mBoxes) {
            for (int j = 0; j < mBoxes[0].length; j++) {
                mBoxe[j].update();
            }
        }
    }

    private void dumpState(String time) {
        Log.e(TAG, time + " called with: mLastDownX = [" + mLastDownX + "], mLastDownY = [" + mLastDownY + "], mLastSelectX = [" + mLastSelectX + "], mLastSelectY = [" + mLastSelectY + "]");
    }
}
