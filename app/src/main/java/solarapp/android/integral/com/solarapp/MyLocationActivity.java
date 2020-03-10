package solarapp.android.integral.com.solarapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import solarapp.android.integral.com.solarapp.databinding.ActivityMyLocationBinding;

public class MyLocationActivity extends AppCompatActivity {

    private static final int RC_LOCATION_PERMISSION = 201;
    ActivityMyLocationBinding activityMyLocationBinding;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyLocationBinding = ActivityMyLocationBinding.inflate(getLayoutInflater());
        setContentView(activityMyLocationBinding.getRoot());
    }


    @Override
    protected void onStart() {
        super.onStart();
        listener();
    }

    private void listener() {

        activityMyLocationBinding.btnCurrentLocation.setOnClickListener(v -> {
            if (checkLocationPermissionGranted()) {
                getCurrentLocation();
            } else {
                //ask for location permission

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RC_LOCATION_PERMISSION);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RC_LOCATION_PERMISSION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getCurrentLocation();

            } else {

                Toast.makeText(this, "Location permission needed..", Toast.LENGTH_SHORT).show();

                boolean shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (shouldShowRequestPermissionRationale) {


                } else {

                    // do something

                    Shape.Circle shape = new Shape.Circle(1.0f);


                }
            }
        }
    }


    private void getCurrentLocation() {

        if (!providerEnabled()) {
            activityMyLocationBinding.txtCurrentLocationAvailability.setText("Provider not enabled");
            activityMyLocationBinding.txtCurrentLocation.setText("Please enable network or gps");
            Toast.makeText(this, "Provider not enabled", Toast.LENGTH_SHORT).show();
            return;
        }

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();

        lastLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                location = task.getResult();
                if (location != null) {

                    activityMyLocationBinding.txtCurrentLocationAvailability.setText("Location available");
                    activityMyLocationBinding.txtCurrentLocation.setText("Found from last: \n" + location.getLatitude() + " " + location.getLongitude());
                    Toast.makeText(MyLocationActivity.this, "Found location from last known location", Toast.LENGTH_SHORT).show();
                    getLocationByAddress();

                } else {

                    Toast.makeText(MyLocationActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    showLoadingLocation();
                    LocationRequest locationrequest = new LocationRequest();
                    locationrequest.setNumUpdates(1).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(0).setFastestInterval(0);
                    fusedLocationProviderClient.requestLocationUpdates(locationrequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);

                            location = locationResult.getLastLocation();
                            activityMyLocationBinding.txtCurrentLocationAvailability.setText("Location available");
                            activityMyLocationBinding.txtCurrentLocation.setText("Found from request: \n" + location.getLatitude() + " " + location.getLongitude());
                            Toast.makeText(MyLocationActivity.this, "Found location from Request", Toast.LENGTH_SHORT).show();
                            getLocationByAddress();
                        }

                        @Override
                        public void onLocationAvailability(LocationAvailability locationAvailability) {
                            super.onLocationAvailability(locationAvailability);

                            activityMyLocationBinding.txtCurrentLocationAvailability.setText(locationAvailability.isLocationAvailable() ? "Location available" : "Unknown");

                        }
                    }, Looper.myLooper());


                }
            }
        });


    }

    private boolean providerEnabled() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));

    }

    private void getLocationByAddress() {

        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            activityMyLocationBinding.txtCurrentLocation.setText("Found address: \n" + addresses.get(0).getLocality() + " " + addresses.get(0).getSubLocality());
            Toast.makeText(MyLocationActivity.this, "Found location address", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {

            activityMyLocationBinding.txtCurrentLocation.setText("Exception while finding address");

        }


    }

    private boolean checkLocationPermissionGranted() {

        int permissionenabled = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return (permissionenabled == PackageManager.PERMISSION_GRANTED);


    }

    private void showLoadingLocation() {

        activityMyLocationBinding.txtCurrentLocation.setText("Loading Location , please wait ..");
    }
}
