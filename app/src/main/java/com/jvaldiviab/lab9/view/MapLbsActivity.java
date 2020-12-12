package com.jvaldiviab.lab9.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jvaldiviab.lab9.R;
import com.jvaldiviab.lab9.databinding.ActivityMapLbsBinding;

import java.util.ArrayList;

public class MapLbsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    ActivityMapLbsBinding binding;

    LocationTrack gps;
    private Double latitude;
    private Double longitude;
    public static LatLng sydney;
    public static GoogleMap mMap;
    public static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapLbsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        latitude = -34d;
        longitude= 150d;
        count=0;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            gps = new LocationTrack(MapLbsActivity.this);

            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                float zoomLevel = 16.0f; //This goes up to 21
                mMap.getMaxZoomLevel();
                Toast.makeText(getApplicationContext(), "This is your Location : \nLatitude: " + latitude + "\nLongitude: " + longitude, Toast.LENGTH_SHORT).show();
            }
            else {
                gps.showSettings();
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
    private static ArrayList<LatLng> posiciones = new ArrayList<>();

    public static void changeMarker(double latitude, double longitude){
        sydney = new LatLng(latitude,longitude);
        posiciones.add(sydney);
        mMap.addPolyline(new PolylineOptions().addAll(posiciones));
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
        count++;

    }
}