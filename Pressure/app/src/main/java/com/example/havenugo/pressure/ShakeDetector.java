package com.example.havenugo.pressure;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by havenugo on 1/5/2017.
 */
public class ShakeDetector implements SensorEventListener {

    private static final float SHAKE_THRESHOLD_GRAVITY = 8.0F;
    private static final int INTERVAL = 1000;
    //private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private OnShakeListener mListener;
    private long mShakeTimestamp;
    private long previousShakeTime;
    private int mShakeCount = 0;

    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    public interface OnShakeListener {
        public void onShake(float x, float y, float z, float ts);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(mListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float gForce = (float) Math.sqrt(x * x + y * y);
            mListener.onShake(x, y, z, System.currentTimeMillis());
            /*if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                Log.d("Force threshold", String.valueOf(SHAKE_THRESHOLD_GRAVITY));
                Log.d("Actual force", String.valueOf(gForce));
                Log.d("X force", String.valueOf(x));
                Log.d("Y force", String.valueOf(y));
                final long now = System.currentTimeMillis();
                if(now - previousShakeTime < INTERVAL) {
                    mShakeCount++;
                }
                else {
                    mShakeCount = 1;
                }
                previousShakeTime = now;
                if(mShakeCount >= 8) {
                    mListener.onShake(gForce);
                }
            }*/
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
