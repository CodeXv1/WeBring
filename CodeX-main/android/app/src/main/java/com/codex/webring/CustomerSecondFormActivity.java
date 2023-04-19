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

public class CustomerSecondFormActivity extends AppCompatActivity {

    private Button mLogout;

    private Button mSubmit;

    private EditText mItemCat, mItemHe, mItemLe, mItemWi;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_second_form);
        mLogout = (Button) findViewById(R.id.logout_d_f);
        mSubmit = (Button) findViewById(R.id.confirm);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                disconnectDriver();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CustomerSecondFormActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });


        mItemCat = (EditText) findViewById(R.id.material);
        mItemHe = (EditText) findViewById(R.id.packaging);
        mItemLe = (EditText) findViewById(R.id.condition);
        mItemWi = (EditText) findViewById(R.id.anyAdditionalInfo);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String itemCat = mItemCat.getText().toString();
                final String itemHei = mItemHe.getText().toString();
                final String itemLe = mItemLe.getText().toString();
                final String itemWi = mItemWi.getText().toString();


                if(itemCat.isEmpty() || itemHei.isEmpty() || itemLe.isEmpty() || itemWi.isEmpty()) {
                    Toast.makeText(CustomerSecondFormActivity.this, "please fill the form", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CustomerSecondFormActivity.this, CustomerMapActivity.class);
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
