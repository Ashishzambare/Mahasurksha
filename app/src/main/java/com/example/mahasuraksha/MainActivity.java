package com.example.mahasuraksha;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;


public class MainActivity extends AppCompatActivity {

    private static FirebaseDatabase firebaseDatabase;

    private ProgressBar progressBar3;
    private FirebaseAuth mAuth;
    private Button sendotp;

    StorageReference reference;
    private Context mContext;
    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN =12;
    private TextView textView2;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            mAuth = FirebaseAuth.getInstance();

            sendotp = findViewById(R.id.sendotp);

            textView2 = findViewById(R.id.textView2);
            SignInButton signInButton = findViewById(R.id.sign_in_button);
            signInButton.setSize(SignInButton.SIZE_STANDARD);
            mContext = getApplicationContext();
            mActivity = MainActivity.this;
            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            FirebaseUser currentUser = mAuth.getCurrentUser();

            sendotp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        checkPermission();

                    }
                }
                });

            findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });


        }



    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(mActivity,Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                mActivity,Manifest.permission.ACCESS_FINE_LOCATION)
                + ContextCompat.checkSelfPermission(
                mActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.ACCESS_FINE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage("Camera, Loction and Write External" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                mActivity,
                                new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                ActivityCompat.requestPermissions(
                        mActivity,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }
    }



    private void signIn(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                mActivity,Manifest.permission.CAMERA)
                || ActivityCompat.shouldShowRequestPermissionRationale(
                mActivity,Manifest.permission.ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(
                mActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this,"Allow all permissions for proper working",Toast.LENGTH_LONG).show();
        }
        else {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
   }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            FirebaseUser currentUser = mAuth.getCurrentUser();
            Intent intent = new Intent(getApplicationContext(),login.class);
            startActivity(intent);

        } catch (ApiException e) {

            Toast.makeText(this, "signInResult:failed code=" ,Toast.LENGTH_LONG).show();

        }
    }




    @Override
    public final void onRequestPermissionsResult(int requestCode,
                                                 @NonNull String[] permissions,
                                                 @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT) .show();
                Toast.makeText(mContext,"Permissions already granted",Toast.LENGTH_SHORT).show();



            }
            else {
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }

        }

    }




