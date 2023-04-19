package com.codex.webring;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerFormActivity extends AppCompatActivity {

    private Button mLogout;

    private Button mSubmit;

    private EditText mItemCat, mItemHe, mItemLe, mItemWi;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_form);
        mLogout = (Button) findViewById(R.id.logout_d_f);
        mSubmit = (Button) findViewById(R.id.confirm);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                disconnectDriver();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CustomerFormActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

      //  mItemCat = (EditText) findViewById(R.id.orderid);
        mItemHe = (EditText) findViewById(R.id.distanceToPickup);
        mItemLe = (EditText) findViewById(R.id.deliverTo);
    //    mItemWi = (EditText) findViewById(R.id.price);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String itemHei = mItemHe.getText().toString();
                final String itemLe = mItemLe.getText().toString();

                if( itemHei.isEmpty() || itemLe.isEmpty()) {
                    Toast.makeText(CustomerFormActivity.this, "please fill the form", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CustomerFormActivity.this, DriverMapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        });
    }

    private void disconnectDriver(){
//        if(mFusedLocationClient != null){
//            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
//        }
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("driversAvailable");

        GeoFire geoFire = new GeoFire(ref);
        geoFire.removeLocation(userId);
    }


}
