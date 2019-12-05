package com.safepayu.wallet.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.safepayu.wallet.R;

import java.util.List;
import java.util.Locale;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.multidex.MultiDex;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;
    List<Address> listAddresses;
    Geocoder geocoder;
    private LinearLayout liSelectLocation;
    private String address, city, state, country, postalCode, knownName, subLocality, subAdmin1, subAdmin, subAdmin2, first, second;
    private TextView tvLatLong;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        tvLatLong = findViewById(R.id.tv_map_lat_long);
        liSelectLocation = findViewById(R.id.li_select_location);
        liSelectLocation.setOnClickListener(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        try {
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            } else {
                Toast.makeText(this, "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }
    protected synchronized void buildGoogleApiClient() {
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
//Showing Current Location Marker on Map
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != location && null != providerList && providerList.size() > 0) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();


            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    address = listAddresses.get(0).getAddressLine(0);
                    subLocality = listAddresses.get(0).getSubLocality();
                    city = listAddresses.get(0).getLocality();
                    state = listAddresses.get(0).getAdminArea();
                    country = listAddresses.get(0).getCountryName();
                    postalCode = listAddresses.get(0).getPostalCode();
                    knownName = listAddresses.get(0).getFeatureName();
                    subAdmin = listAddresses.get(0).getSubAdminArea();
                    subAdmin1 = listAddresses.get(0).getSubThoroughfare();
                    subAdmin2 = listAddresses.get(0).getPremises();


                    String[] separated = address.split(",");
                    first = separated[0];// this will contain "Fruit"
                    second = separated[1];
                    Log.d("DDRE", first + second);
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country);
                    tvLatLong.setText(address);
                    Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("Location Address Loader", "Unable connect to Geocoder", e);
                e.printStackTrace();
            }
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mCurrLocationMarker = mMap.addMarker(markerOptions.draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.d("System out", "onMarkerDragStart..." + marker.getPosition().latitude + "..." + marker.getPosition().longitude);
                //   Toast.makeText(MapActivity.this, String.format("Drag from"+marker.getPosition().latitude + marker.getPosition().longitude),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Log.i("System out", "onMarkerDrag...");
                //    Toast.makeText(MapActivity.this, String.format("Drag To"+marker.getPosition().latitude + marker.getPosition().longitude),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.d("System out", "onMarkerDragEnd..." + marker.getPosition().latitude + "..." + marker.getPosition().longitude);
                //     Toast.makeText(MapActivity.this, String.format("Drag end"+marker.getPosition().latitude + marker.getPosition().longitude),Toast.LENGTH_SHORT).show();
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    listAddresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                    if (null != listAddresses && listAddresses.size() > 0) {
                        address = listAddresses.get(0).getAddressLine(0);
                        subLocality = listAddresses.get(0).getSubLocality();
                        city = listAddresses.get(0).getLocality();
                        state = listAddresses.get(0).getAdminArea();
                        country = listAddresses.get(0).getCountryName();
                        postalCode = listAddresses.get(0).getPostalCode();
                        knownName = listAddresses.get(0).getFeatureName();
                        subAdmin = listAddresses.get(0).getSubAdminArea();
                        subAdmin1 = listAddresses.get(0).getSubThoroughfare();


                        String[] separated = address.split(",");
                        first = separated[0];// this will contain "Fruit"
                        second = separated[1];

                        tvLatLong.setText(address);
                        Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Location Address Loader", "Unable connect to Geocoder", e);
                    e.printStackTrace();
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            }
        });



    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_select_location:
                Toast.makeText(this, "Location select", Toast.LENGTH_LONG).show();


                Intent intent = new Intent(MapsActivity.this, AddUpdateAddress.class);
                if (subLocality == null) {
                    intent.putExtra("select_locality", knownName + " " + second);
                } else {
                    intent.putExtra("select_locality", knownName + " " + second + " " + subLocality);
                }

                intent.putExtra("select_country", country);
                intent.putExtra("select_pincode", postalCode);
                intent.putExtra("select_state", state);
                intent.putExtra("select_city", city);
                setResult(Activity.RESULT_OK, intent);
                finish();

                break;
        }
    }
}