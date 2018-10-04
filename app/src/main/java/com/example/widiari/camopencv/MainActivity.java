package com.example.widiari.camopencv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaActionSound;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    // Used to load the 'native-lib' library on application startup.

    private static final String TAG = "OCVSample:Activity";
    myJavaCamView cameraBridgeViewBase;
    Button btn_cam;
    Mat mat;


    private BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface.SUCCESS:{
                    Log.i(TAG,"OpenCV loaded succesfully");
                    cameraBridgeViewBase.enableView();
                }break;
                default:
                {
                    super.onManagerConnected(status);
                }break;
            }
        }
    };
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        cameraBridgeViewBase=findViewById(R.id.javacam);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);

        btn_cam=findViewById(R.id.btn_cam);
        btn_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaActionSound sound = new MediaActionSound();
                sound.play(MediaActionSound.SHUTTER_CLICK);
                Log.i(TAG,"on Button Click");
                Date date = new Date();
                String currentDateandTime = date.toString();
                String fileName = Environment.getExternalStorageDirectory().getPath() + "/sample_"
                        + currentDateandTime + ".jpeg";
                cameraBridgeViewBase.takePicture(fileName);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        if (!OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(),"There is problem in OpenCV",Toast.LENGTH_LONG).show();

        }else {
            mLoaderCallBack.onManagerConnected(mLoaderCallBack.SUCCESS);
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        cameraBridgeViewBase.disableView();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
//        if (cameraBridgeViewBase!=null)
//            cameraBridgeViewBase.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height){
        mat=new Mat(width,height, CvType.CV_8UC4);

    }

    @Override
    public void onCameraViewStopped() {
        mat.release();
    }

    @Override
    public Mat onCameraFrame (CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        mat=inputFrame.rgba();
        Mat mRgbaT=mat.t();
        Core.flip(mat.t(),mRgbaT,1);
        Imgproc.resize(mRgbaT,mRgbaT,mat.size());
        return mRgbaT;
    }
}
