package com.mobven.weatherforecast.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.mobven.weatherforecast.BaseActivity;
import com.mobven.weatherforecast.R;
import com.mobven.weatherforecast.core.constant.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class CapturePhotoActivity extends BaseActivity implements AutoFocusCallback, View.OnClickListener, Camera.PictureCallback {

    private CameraPreview mPreview;
    public final static String DEBUG_TAG = "CapturePhotoActivity";

    ImageView img_capture;

    String path;
    RelativeLayout rel_layout;
    private int cameraId = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.WRITE_EXTERNAL_PERMISSION) {
            mPreview.takePicture(CapturePhotoActivity.this);
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_photo);

        initUI();
    }

    private void initUI() {
        img_capture = findViewById(R.id.img_capture);
        img_capture.setOnClickListener(this);
        rel_layout = findViewById(R.id.rel_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
                    .show();
        } else {
            cameraId = findCamera();
            if (cameraId < 0) {
                Toast.makeText(this, "No front back camera found.",
                        Toast.LENGTH_LONG).show();
            } else {
                mPreview = new CameraPreview(this, cameraId, CameraPreview.LayoutMode.FitToParent);
                LayoutParams previewLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
                rel_layout.addView(mPreview, 0, previewLayoutParams);
            }
        }

    }


    private int findCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
                Log.d(DEBUG_TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_capture:
                if (checkIfAlreadyhavePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    mPreview.takePicture(CapturePhotoActivity.this);
                } else {
                    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Constant.WRITE_EXTERNAL_PERMISSION);
                }
                break;
        }
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        File pictureFileDir = fileOutput();

        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

            Toast.makeText(appCompatActivity, "Can't create directory to save image.",
                    Toast.LENGTH_LONG).show();
            return;

        }

        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(data);
            fos.close();

            save();
        } catch (Exception error) {
            Toast.makeText(appCompatActivity, "Image could not be saved.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void save() {

        List<String> pathList;
        try {
            if (sharedPreference.getPathList() == null) {
                pathList = new ArrayList<>();
            } else {
                pathList = sharedPreference.getPathList();
            }
            pathList.add(path);
            sharedPreference.setPathList(pathList);
            finishActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void finishActivity() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public File fileOutput() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Constant.IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        path = mediaStorageDir.getPath() + File.separator + System.currentTimeMillis() + ".jpeg";
        return mediaStorageDir;
    }


}