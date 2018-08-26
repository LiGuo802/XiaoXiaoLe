package cn.twoer.xiaoxiaole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.twoer.xiaoxiaole.box.BaseBox;
import cn.twoer.xiaoxiaole.box.BoxNormal;

public class MainActivity extends AppCompatActivity {

    GameLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameLayout = findViewById(R.id.gameLayout);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        gameLayout.setData(getData());
    }

    public BaseBox[][] getData() {
        BaseBox[][] data = new BaseBox[9][9];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = new BoxNormal(BoxNormal.getRandomType(), this);
            }
        }
        return data;
    }
}
