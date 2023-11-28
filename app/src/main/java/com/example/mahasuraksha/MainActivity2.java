package com.example.mahasuraksha;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_LOCATION = 10 ;
    private EditText PersonName;
    public EditText Phone;
    private EditText Address;
    private TextView TextLatLong;
    private Button verify;
    private CheckBox checkBox;
    private EditText Complaint;
    private Button submit;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        PersonName = findViewById(R.id.PersonName);
        Phone = findViewById(R.id.Phone);
        Address = findViewById(R.id.Address);
        TextLatLong = findViewById(R.id.TextLatLong);
        checkBox = findViewById(R.id.checkBox);
        Complaint = findViewById(R.id.Complaint);
        submit = findViewById(R.id.submit);
        DatabaseReference reference;
        FirebaseDatabase firebaseDatabase ;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Phone.length() != 10 ){
                    Toast.makeText(MainActivity2.this,"Enter Valid 10 digit no",Toast.LENGTH_LONG).show();
                    Phone.setError("Error");
                }
                else {


                    DatabaseReference reference;
                    reference = FirebaseDatabase.getInstance().getReference("server/saving-data/fireblog");
                    String personName = MainActivity2.this.PersonName.getEditableText().toString();
                    String phone = MainActivity2.this.Phone.getEditableText().toString();
                    String address = MainActivity2.this.Address.getEditableText().toString();
                    String textLatLong = MainActivity2.this.TextLatLong.getEditableText().toString();
                    String complaint = MainActivity2.this.Complaint.getEditableText().toString();
                    UserHelperClass helperClass = new UserHelperClass(personName, phone, address, textLatLong, complaint);
                    reference.child(personName).setValue(helperClass);
                    Toast.makeText(MainActivity2.this,"Your Complaint get Registered",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity2.this,Tips.class);
                    startActivity(intent);
                }
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity2.this.getCurrentlocation();            }
        });
    }
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MainActivity2.REQUEST_PERMISSION_LOCATION && grantResults.length > 0) {
            this.getCurrentlocation();
        } else {
            Toast.makeText(this, "Access to Location denied", Toast.LENGTH_LONG).show();
        }
    }

    private void getCurrentlocation() {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(final LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                LocationServices.getFusedLocationProviderClient(MainActivity2.this)
                                        .removeLocationUpdates(this);
                                if (locationResult != null && locationResult.getLocations().size() > 0) {
                                    final int latestLocationIndex = locationResult.getLocations().size() - 1;
                                    final double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                                    final double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                                    com.example.mahasuraksha.MainActivity2.this.TextLatLong.setText("Lattitude :"+ latitude+"Longitude"+longitude);
                                }

                            }
                        }, Looper.getMainLooper()
                );
    }

}