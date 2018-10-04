package com.example.widiari.camopencv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    Button btn_cap;
    Button btn_gray;
    Button btn_canny;
    Button btn_gaussian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_cap=findViewById(R.id.btn_cap);
        btn_cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btn_gray=findViewById(R.id.btn_gray);
        btn_gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this,GrayActivity.class);
                startActivity(intent);
            }
        });

        btn_canny=findViewById(R.id.btn_canny);
        btn_canny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this,CannyActivity.class);
                startActivity(intent);
            }
        });

        btn_gaussian=findViewById(R.id.btn_gaussian);
        btn_gaussian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this,GaussianActivity.class);
                startActivity(intent);
            }
        });


    }
}
