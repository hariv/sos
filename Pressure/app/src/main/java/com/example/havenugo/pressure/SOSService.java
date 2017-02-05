package com.example.havenugo.pressure;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by havenugo on 1/21/2017.
 */
public class SOSService extends Service{
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private OkHttpClient client = new OkHttpClient();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("oliver", "queen");
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(float x, float y, float z, float ts) {
                Request request = new Request.Builder().url("http://pressureapp.herokuapp.com/save?name=Anmol&fx="+String.valueOf(x)+"&fy="+String.valueOf(y)+"&fz="+String.valueOf(z)+"&ts="+String.valueOf(ts)).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.v("Error", e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.v("Sent", "Values");
                    }
                });
                /*Log.d("Force", String.valueOf(force));
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+918971203911", null, String.valueOf(force), null, null);
                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(force), Toast.LENGTH_LONG);
                toast.show();*/
            }
        });
        //Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
    }

    public void onStart() {
        Log.d("Green", "Arrow");
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        mSensorManager.unregisterListener(mShakeDetector);
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }
}
