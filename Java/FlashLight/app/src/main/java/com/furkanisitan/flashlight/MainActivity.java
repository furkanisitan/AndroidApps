package com.furkanisitan.flashlight;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private boolean isFlashlightOn = false;
    private ImageButton flashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean deviceHasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (deviceHasCameraFlash) {
            init();
            imageButton_onClick();
        } else
            viewAlertDialog();
    }

    private void init() {
        flashImage = findViewById(R.id.imageButton);
        flashImage.setBackgroundResource(R.drawable.off);
    }

    public void imageButton_onClick() {
        flashImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFlashlightOn ^= true;
                toggleFlashLight();
                toggleFlashImage();
            }
        });
    }

    private void toggleFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                CameraManager camManager = (CameraManager) getSystemService(CAMERA_SERVICE);
                String cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
                camManager.setTorchMode(cameraId, isFlashlightOn);
            } else {
                Camera camera = Camera.open();
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(isFlashlightOn ? Camera.Parameters.FLASH_MODE_TORCH :
                        Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.stopPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleFlashImage() {
        flashImage.setBackgroundResource(isFlashlightOn ? R.drawable.on : R.drawable.off);
    }

    private void viewAlertDialog() {
        AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
        alert.setTitle("Hata!");
        alert.setMessage("Cihazınız flash desteklemiyor...");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Tamam",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                });
        alert.show();
    }

}
