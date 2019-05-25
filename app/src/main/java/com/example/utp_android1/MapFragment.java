package com.example.utp_android1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MapFragment extends SupportMapFragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    private static final String TAG = ">>>> MapFragment <<<<";
    private GoogleMap mMap;
    private View mapView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "[onMapReady]");
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);

        // Base location
        LatLng lima = new LatLng(-12.0463731, -77.042754);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lima, 11.0f));

        // enableMyLocation();
    }

    public GoogleMap getMap() {
        return mMap;
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


        // ************************
        //
        // Aquí lanzar el detalle de un punto de acopio
        //
        // ************************

        return false;
    }

    /*
    private void enableMyLocation() {
        Log.d(TAG, "[enableMyLocation]");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
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


    // Displays a dialog with error message explaining that the location permission is missing.
    private void showMissingPermissionError() {
        Log.d(TAG, "[showMissingPermissionError]");
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
    */
}
