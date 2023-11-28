package com.example.mahasuraksha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    private TextView textView;
    private Button button3;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView=(TextView) findViewById(R.id.textView);
        button3=(Button)findViewById(R.id.button3);
        imageView=(ImageView)findViewById(R.id.imageView);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,Tips.class);
                startActivity(intent);
            }
        });


    }

}