package com.example.mahasuraksha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Tips<imageView> extends AppCompatActivity {
    private Integer images[] = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4,R.drawable.pic5, R.drawable.pic6, R.drawable.pic7, R.drawable.pic8, R.drawable.pic9, R.drawable.pic10, R.drawable.pic11, R.drawable.pic12, R.drawable.pic13, R.drawable.pic14, R.drawable.pic15};
    private int currImage = 1;
    private Button button2;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        button2 = findViewById(R.id.button2);

        setInitialImage();
        setImageRotateListener();
    }


    private void setImageRotateListener() {
        final Button rotatebutton = (Button) findViewById(R.id.btnRotateImage);
        rotatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                currImage++;
                if (currImage == 15) {
                    currImage = 0;
                }
                setCurrentImage();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tips.this,Camera.class);
                startActivity(intent);

            }
        });
        final Button backimage = (Button) findViewById(R.id.backimage);
        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                currImage--;
                if (currImage == 0) {
                    currImage = 15;
                }
                setCurrentImage();

            }

        });
    }


        private void setInitialImage () {
            setCurrentImage();
        }

        private void setCurrentImage() {
            final ImageView imageView = (ImageView) findViewById(R.id.imageDisplay);
            imageView.setImageResource(images[currImage]);
        }
    }
