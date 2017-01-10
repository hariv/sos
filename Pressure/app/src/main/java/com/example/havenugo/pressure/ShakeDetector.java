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

    private static final float SHAKE_THRESHOLD_GRAVITY = 1.40F;
    private static final int INTERVAL = 1000;
    //private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private OnShakeListener mListener;
    //private long mShakeTimestamp;
    private long previousShakeTime;
    private int mShakeCount;

    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    public interface OnShakeListener {
        public void onShake(float force);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(mListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;
            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                Log.d("Force threshold", String.valueOf(SHAKE_THRESHOLD_GRAVITY));
                Log.d("Actual force", String.valueOf(gForce));
                final long now = System.currentTimeMillis();
                Log.d("mShakeTimestamp", String.valueOf(previousShakeTime));
                Log.d("now", String.valueOf(now));
                if(now - previousShakeTime < INTERVAL) {
                    mShakeCount++;
                }
                else {
                    mShakeCount = 1;
                }
                previousShakeTime = now;
                /*if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }

                // reset the shake count after 3 seconds of no shakes
                if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeCount = 0;
                }

                mShakeTimestamp = now;*/
                Log.d("mShakeCount: ", String.valueOf(mShakeCount));
                if(mShakeCount >= 7) {
                    mListener.onShake(gForce);
                    mShakeCount = 0;
                }
                else {
                    Log.d("Peace", "podu");
                }
                //mListener.onShake(gForce, mShakeCount);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
