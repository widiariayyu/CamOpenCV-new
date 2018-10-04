package com.example.widiari.camopencv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageActivity extends AppCompatActivity {
    private static String TAG = "ImageActivity";
    Button galleryBtn, BrightBtn, ContrastBtn, flipBtn, btn_canny, btn_gaussian;
    ImageView previewIv;
    Bitmap bitmap,resultBitmap;
    Uri imageUri;
    int x = 0, y = 0, width, height, i;
    static {
        OpenCVLoader.initDebug();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        galleryBtn = findViewById(R.id.gallery_btn);
        BrightBtn = findViewById(R.id.bright_btn);
        ContrastBtn= findViewById(R.id.contrast_btn);
        flipBtn = findViewById(R.id.flip_btn);
        previewIv = findViewById(R.id.image_preview);
        btn_canny = findViewById(R.id.btn_canny);
        btn_gaussian = findViewById(R.id.btn_gaussian);

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,100);
            }
        });


        flipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
                width = bitmap.getWidth();
                height = bitmap.getHeight();
                int[][] pixel = new int[width][height];
                resultBitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
                for(x=0;x<width;x++){
                    for(y=0;y<height;y++) {
                        pixel[x][y] = bitmap.getPixel(x, y);
                    }
                }
                x=0;
                for(i=width-1;i>=0;i--){
                    for(y=0;y<height;y++) {
                        resultBitmap.setPixel(i,y,pixel[x][y]);
                    }
                    x++;
                }
                previewIv.setImageBitmap(resultBitmap);

            }
        });

        btn_canny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
                    Mat sampledImage = new Mat(bitmap.getHeight(),bitmap.getWidth(),CvType.CV_8UC4);
                    Utils.bitmapToMat(bitmap,sampledImage);


                    Mat gray = new Mat();
                    Imgproc.cvtColor(sampledImage, gray, Imgproc.COLOR_RGB2GRAY);
                    Mat result = new Mat(bitmap.getHeight(),bitmap.getWidth(),CvType.CV_8UC4);
                    Imgproc.Canny(gray, result, 100, 200);
                    resultBitmap = Bitmap.createBitmap(result.cols(),result.rows(),Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(result,resultBitmap);
                    previewIv.setImageBitmap(resultBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        btn_gaussian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);

                    Mat sampledImage = new Mat(bitmap.getHeight(),bitmap.getWidth(),CvType.CV_8UC4);
                    Mat result = new Mat(bitmap.getHeight(),bitmap.getWidth(),CvType.CV_8UC4);

                    Utils.bitmapToMat(bitmap,sampledImage);
                    Imgproc.GaussianBlur(sampledImage,result, new Size(21,21),0);
                    resultBitmap = Bitmap.createBitmap(result.cols(),result.rows(),Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(result,resultBitmap);

                    previewIv.setImageBitmap(resultBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                previewIv.setImageBitmap(bitmap);
                width = bitmap.getWidth();
                height = bitmap.getHeight();
                int[][] pixel = new int[width][height];
                int[][] redValue = new int[width][height];
                int[][] greenValue = new int[width][height];
                int[][] blueValue = new int[width][height];
                String[] matrix = new String[width];

                Log.i(TAG,"Matrix Value: ");
                for(x=0;x<width;x++){
                    matrix[x] = "| ";
                    for(y=0;y<height;y++) {
                        pixel[x][y] = bitmap.getPixel(x, y);
                        redValue[x][y] = Color.red(pixel[x][y]);
                        greenValue[x][y] = Color.green(pixel[x][y]);
                        blueValue[x][y] = Color.blue(pixel[x][y]);
                        matrix[x] = matrix[x]+""+redValue[x][y]+", "+greenValue[x][y]+", "+blueValue[x][y]+" | ";
                    }
                    Log.i(TAG,""+matrix[x]);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
