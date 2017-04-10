package nl.vanbijleveld.owd.workers;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.*;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import android.content.pm.PackageManager;
import android.util.Log;


import com.google.android.gms.common.api.GoogleApiClient;

import nl.vanbijleveld.owd.MainActivity;

public class MyLocation implements ConnectionCallbacks, OnConnectionFailedListener {

    private final TaskExecutor executor;
    //private final LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location myLocation;
    private GoogleApiClient mGoogleApiClient;
    private Context context;

    public MyLocation(TaskExecutor executor) {
        this.executor = executor;
        this.context = this.executor.getTask().getContext();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this.context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        int hasLocationPermission = ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {

            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
            builder.setAlwaysShow(true);
            LocationSettingsRequest result = LocationServices.SettingsApi.checkLocationSettings(client, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            executor.updateLocation(myLocation);
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(MainActivity.this, GPS_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {

                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        //}

        //    myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
         //   executor.updateLocation(myLocation);
         //   this.endUpdates();
        }else{
            Log.w("MyLocation","No Location acces granted");
        }
    }


    @Override
    public void onConnectionSuspended(int i){
        this.endUpdates();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result){
        this.endUpdates();
    }

    public void fetch() {
        mGoogleApiClient.connect();
        //super.onStart();
    }

    public void endUpdates() {
        mGoogleApiClient.disconnect();
        //super.onStop();
    }
}
