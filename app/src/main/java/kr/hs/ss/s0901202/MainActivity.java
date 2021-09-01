package kr.hs.ss.s0901202;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    GameGraphicView ggv;
    SensorManager sm;
    Sensor sen_acc;
    float[] senValue = null;
    int ball_x =0, ball_y = 0,
            width, height,
            wall_x, wall_y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ggv = new GameGraphicView(getApplicationContext());
        setContentView(ggv);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sen_acc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener((SensorEventListener)this,
                        sen_acc, SensorManager.SENSOR_DELAY_GAME);

        width = getResources().getDisplayMetrics().widthPixels;
        height = getResources().getDisplayMetrics().heightPixels;
        ball_x = width/2;
        ball_y = height/4;
        wall_x = width/3*2;
        wall_y = height/2;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            senValue = event.values;

            ball_x = ball_x - (int) senValue[0] * 2;
            ball_y = ball_y + (int) senValue[1] * 2;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class GameGraphicView extends View {
        public GameGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            // 그림 그리는 곳
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setTextSize(50);
            p.setTextAlign(Paint.Align.CENTER);

            canvas.drawText("x:" + senValue[0], 100, 100,p);
            canvas.drawText("y:" + senValue[1], 200, 100,p);
            canvas.drawText("z:" + senValue[2], 300, 100,p);

            p.setColor(Color.RED);
            canvas.drawRect(0, wall_y - 15, wall_x, wall_y, p);

            p.setColor(Color.GREEN);
            canvas.drawCircle(width/2, height/4, 100, p);
            canvas.drawCircle(width/2, height/4*3, 100, p);

            p.setColor(Color.BLUE);
            canvas.drawText("●",ball_x, ball_y, p);

        }
    }
}


