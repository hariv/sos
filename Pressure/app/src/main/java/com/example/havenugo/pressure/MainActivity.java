package com.example.havenugo.pressure;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private Button buttonStart;
    private Button buttonStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);

        /*mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(float force) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+919916421310", null, "Help me please, I'm being attacked.", null, null);
                Log.d("Message", "Help me please, I'm being attacked.");
                //Log.d("Force", String.valueOf(force));
                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(force), Toast.LENGTH_LONG);
                toast.show();
                /*if(count >= 10) {
                    Log.d("Count", String.valueOf(count));
                    Log.d("Force", String.valueOf(force));
                    Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(force), Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    Log.d("Peace podu", "machi");
            /*    }
            }
        });*/
    }
    @Override
    public void onResume() {
        super.onResume();
        //mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        //mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonStart:
                Log.d("clicked", "start");
                startService(new Intent(this, SOSService.class));
                break;
            case R.id.buttonStop:
                Log.d("clicked", "stop");
                stopService(new Intent(this, SOSService.class));
        }

    }
}
