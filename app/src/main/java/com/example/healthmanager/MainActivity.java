package com.example.healthmanager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1001;
    private SensorManager sensorManager;
    private Sensor heartRateSensor;
    private SensorEventListener heartRateListener;
    private ApiService apiService;  // Retrofit API service

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")  // Replace with your server's IP or URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Initialize the sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Initialize the heart rate sensor
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        // Create a sensor event listener
        heartRateListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
                    float heartRate = event.values[0];  // Get heart rate value
                    Log.d("HeartRate", "Heart Rate: " + heartRate);  // Display in logs

                    // Create heart rate data object
                    HeartRateData heartRateData = new HeartRateData(heartRate);

                    // Send the heart rate data to the server
                    apiService.sendHeartRate(heartRateData).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("API", "Data sent successfully");
                            } else {
                                Log.d("API", "Failed to send data: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("API", "Error sending data", t);
                        }
                    });
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Handle accuracy changes (if needed)
            }
        };

        // Check and request permission
        if (isPermissionGranted()) {
            startHeartRateMonitoring();
        } else {
            requestPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPermissionGranted() && heartRateSensor != null) {
            // Register the listener to start receiving heart rate data
            sensorManager.registerListener(heartRateListener, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener to stop receiving heart rate data
        if (heartRateListener != null) {
            sensorManager.unregisterListener(heartRateListener);
        }
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.BODY_SENSORS}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start sensor access
                startHeartRateMonitoring();
            } else {
                // Permission denied, handle accordingly
                Log.d("Permission", "BODY_SENSORS permission denied.");
            }
        }
    }

    private void startHeartRateMonitoring() {
        if (heartRateSensor != null) {
            sensorManager.registerListener(heartRateListener, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.d("Sensor", "Heart Rate Sensor not available.");
        }
    }
}
