package com.example.widiari.camopencv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FrontActivity extends AppCompatActivity {
    Button live_btn;
    Button open_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        live_btn=findViewById(R.id.live_btn);
        live_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FrontActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        open_btn=findViewById(R.id.open_btn);
        open_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FrontActivity.this,ImageActivity.class);
                startActivity(intent);
            }
        });
    }
}
