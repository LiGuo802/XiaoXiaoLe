package cn.twoer.xiaoxiaole;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import cn.twoer.xiaoxiaole.box.BaseBox;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            BaseBox.SIZE = (dm.widthPixels - (BaseBox.NUM + 1) * BaseBox.MARGIN) / BaseBox.NUM;
            Log.e("liguo", BaseBox.SIZE + "");
        }
    }
}
