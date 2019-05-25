package com.example.utp_android1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
// import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

// public class HomeActivity extends FragmentActivity implements OnMapReadyCallback {
public class GoogleMapActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    private static final String TAG = ">>>> HomeActivity <<<<";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    private View mapView;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "[enableMyLocation]");
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);

        // Base location
        LatLng lima = new LatLng(-12.0463731, -77.042754);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lima, 11.0f));

        enableMyLocation();
    }

    private MarkerOptions createMarker(Double latitude, Double longitude, String title) {
        return new MarkerOptions().position(new LatLng(latitude, longitude)).title(title).snippet(title);
    }

    private void setMarkers() {
        // new MarkerOptions().position(lima).title("Lima, Perú")

        // mMap.addMarker(new MarkerOptions().position(lima).title("Lima, Perú"));
        ArrayList<MarkerOptions> markerOptionsList = new ArrayList<MarkerOptions>();
        markerOptionsList.add(createMarker(-12.053794, -77.036050, "Punto de Acopio 1"));
        markerOptionsList.add(createMarker(-12.055574, -77.033615, "Punto de Acopio 2"));
        markerOptionsList.add(createMarker(-12.059772, -77.032963, "Punto de Acopio 3"));

        for(MarkerOptions markerOption : markerOptionsList) {
            mMap.addMarker(markerOption);
        }

        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        Toast.makeText(this, "Mostrar detalle sobre " + marker.getTitle(), Toast.LENGTH_SHORT).show();

        return false;
    }

    private void enableMyLocation() {
        Log.d(TAG, "[enableMyLocation]");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
            return;
        }

        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Move the camera in current location
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13.0f));

        // Access to the location has been granted to the app.
        mMap.setMyLocationEnabled(true);

        // Get the button view
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        // and next place it, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 0, 48, 48);

        //locationButton.clip

        setMarkers();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "[onRequestPermissionsResult] code: " + requestCode);

        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
            Log.d(TAG, "[onRequestPermissionsResult] Permission Denied");
        }
    }

    @Override
    protected void onResumeFragments() {
        Log.d(TAG, "[onResumeFragments]");

        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        Log.d(TAG, "[showMissingPermissionError]");
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
}
