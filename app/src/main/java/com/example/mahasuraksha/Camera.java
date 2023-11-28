package com.example.mahasuraksha;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Camera extends AppCompatActivity {

    private Button upload;
    private Button choose;
    private ImageView photo;
    private Bitmap myimage;
    private ProgressDialog mProgress ;
    private FirebaseStorage firebaseStorage;
    private StorageReference mStorageRef;

    private TextView textView3;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        upload = (Button) findViewById(R.id.upload);
        choose = (Button) findViewById(R.id.choose);
        photo = (ImageView) findViewById(R.id.myimage);
        mProgress = new ProgressDialog(this);



        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

            }

        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();

            }
        });

    }


    public void upload(){
        final ProgressBar p= findViewById(R.id.p);
        p.setVisibility(View.VISIBLE);
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        myimage.compress(Bitmap.CompressFormat.JPEG, 100, stream=stream);
        final String random = UUID.randomUUID().toString();
        StorageReference imageRef = mStorageRef.child("image/"+random);

        byte[] b = stream.toByteArray();
        imageRef.putBytes(b)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Camera.this,"Photo recieved your compaint get registerd will contact soon",Toast.LENGTH_LONG).show();;
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUri = uri;
                                Intent intent = new Intent(Camera.this,MainActivity2.class);
                                startActivity(intent);
                            }
                            });
                        }



                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                p.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(Camera.this,"Failed to get Compaint chake connection",Toast.LENGTH_LONG).show();
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==RESULT_OK) {
            myimage=(Bitmap)data.getExtras().get("data");
            photo.setImageBitmap(myimage);

        }

    }

}





