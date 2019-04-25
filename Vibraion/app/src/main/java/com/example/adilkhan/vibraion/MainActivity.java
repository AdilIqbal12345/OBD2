package com.example.adilkhan.vibraion;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private float startTime;

    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;

    private SensorManager sensorManager;

    TextView x;
    TextView y;
    TextView z;

    String sx, sy, sz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = (TextView) findViewById (R.id.textView2);
        y = (TextView) findViewById (R.id.textView3);
        z = (TextView) findViewById (R.id.textView4);


        startTime = System.currentTimeMillis();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorManager.registerListener(this, sensorManager.getDefaultSensor
                (Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float sensoreventime = (event.timestamp)/1000000;
        float timeDuration = startTime-sensoreventime;

        // TODO Auto-generated method stub
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            float xVal = event.values[0];
            float yVal = event.values[1];
            float zVal = event.values[2];


            sx = "X Value : <font color = '#800080'> " + xVal + "</font>";
            sy = "Y Value : <font color = '#800080'> " + yVal + "</font>";
            sz = "Z Value : <font color = '#800080'> " + zVal + "</font>";

            x.setText(Html.fromHtml(sx));
            y.setText(Html.fromHtml(sy));
            z.setText(Html.fromHtml(sz));

            ///// For Acceleration
            double Xpow2 = Math.pow(xVal,2);
            double Ypow2 = Math.pow(yVal,2);
            double Zpow2 = Math.pow(zVal,2);

            double xyzSummation = Xpow2+Ypow2+Zpow2;

            double frequency=0;
            // This timestep's delta rotation to be multiplied by the current rotation
            // after computing it from the gyro sample data.
            if (timeDuration != 0) {
               // final float dT = (timestamp1-event.timestamp) * NS2S;
                double accreleration = Math.sqrt(xyzSummation);
                double velocity = accreleration*timeDuration;
                 frequency = accreleration/((velocity*2*3.14));
            }

            String sfreq = "Frequency : <font color = '#800080'> " + frequency + "</font>";
            TextView tvfequency=(TextView) findViewById (R.id.frequency);
            tvfequency.setText(Html.fromHtml(sfreq));
        }
    }


}
