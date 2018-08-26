package cn.twoer.xiaoxiaole;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import cn.twoer.xiaoxiaole.box.BaseBox;

public class AnimUtils {

    public static Animator getSwapAnim(BaseBox box1, BaseBox box2, int x1, int y1, int x2, int y2) {

        float box1Start, box1End;
        float box2Start, box2End;
        String propertyName;

        if (x1 == x2) {
            propertyName = "x";
            if (y1 < y2) {
                box1Start = box1.getX();
                box1End = box1.getX() + BaseBox.SIZE + BaseBox.MARGIN;
                box2Start = box2.getX();
                box2End = box2.getX() - BaseBox.SIZE - BaseBox.MARGIN;
            } else {
                box1Start = box1.getX();
                box1End = box1.getX() - BaseBox.SIZE - BaseBox.MARGIN;
                box2Start = box2.getX();
                box2End = box2.getX() + BaseBox.SIZE + BaseBox.MARGIN;
            }
        } else {
            propertyName = "y";
            if (x1 < x2) {
                box1Start = box1.getY();
                box1End = box1.getY() + BaseBox.SIZE + BaseBox.MARGIN;
                box2Start = box2.getY();
                box2End = box2.getY() - BaseBox.SIZE - BaseBox.MARGIN;
            } else {
                box1Start = box1.getY();
                box1End = box1.getY() - BaseBox.SIZE - BaseBox.MARGIN;
                box2Start = box2.getY();
                box2End = box2.getY() + BaseBox.SIZE + BaseBox.MARGIN;
            }
        }

        box1.setZ(box2.getZ() + 1);

        ObjectAnimator animTransBox1 = ObjectAnimator.ofFloat(box1, propertyName, box1Start, box1End);
        ObjectAnimator animScaleXBox1 = ObjectAnimator.ofFloat(box1, "scaleX", 1f, 1.25f, 1f);
        ObjectAnimator animScaleYBox1 = ObjectAnimator.ofFloat(box1, "scaleY", 1f, 1.25f, 1f);
        ObjectAnimator animScaleXBox2 = ObjectAnimator.ofFloat(box2, "scaleX", 1f, 0.75f, 1f);
        ObjectAnimator animScaleYBox2 = ObjectAnimator.ofFloat(box2, "scaleY", 1f, 0.75f, 1f);
        ObjectAnimator animTransBox2 = ObjectAnimator.ofFloat(box2, propertyName, box2Start, box2End);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animTransBox1, animTransBox2, animScaleXBox1, animScaleXBox2, animScaleYBox1, animScaleYBox2);
        set.setDuration(250);
        return set;
    }

}
