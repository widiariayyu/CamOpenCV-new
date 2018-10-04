package com.example.widiari.camopencv;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;

import org.opencv.android.JavaCameraView;

import java.io.FileOutputStream;

public class myJavaCamView extends JavaCameraView  implements android.hardware.Camera.PictureCallback {

    private static final String TAG = "OpenCV";
    private String mPictureFileName;

    public  void takePicture(final String fileName){
        Log.i(TAG,"Taking Pic");
        this.mPictureFileName = fileName;
        mCamera.setPreviewCallback(null);
        mCamera.takePicture(null,null,this);
    }

    public myJavaCamView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
        Log.i(TAG, "Saving a bitmap to file");
        mCamera.startPreview();
        mCamera.setPreviewCallback(this);

        try {
            FileOutputStream fos = new FileOutputStream(mPictureFileName);
            fos.write(data);
            fos.close();
        }catch (java.io.IOException e)
        {
            Log.e("Picture Demo","Exception in PhotoCallback", e);
        }
    }
}
