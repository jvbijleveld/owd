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
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.*;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import android.content.pm.PackageManager;
import android.util.Log;


import com.google.android.gms.common.api.GoogleApiClient;

import nl.vanbijleveld.owd.MainActivity;

/*
public class MyLocation implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final TaskExecutor executor;
    //private final LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private LocationRequest mLocationRequest;
    private Location myLocation;
    private GoogleApiClient mGoogleApiClient;
    private Context context;

    private static final long POLLING_FREQ = 1000 * 30;
    private static final long FASTEST_UPDATE_FREQ = 1000 * 5;
    private static final float MIN_ACCURACY = 25.0f;
    private static final float MIN_LAST_READ_ACCURACY = 500.0f;

    public MyLocation(TaskExecutor executor) {
        this.executor = executor;
        this.context = this.executor.getTask().getContext();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(POLLING_FREQ);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_FREQ);

        mGoogleApiClient = new GoogleApiClient.Builder(this.context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        int hasLocationPermission = ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            executor.updateLocation(myLocation);
           this.endUpdates();
        }else{
            Log.w("MyLocation","No Location acces granted");
        }
    }

    @Override
    public void onStatusChanged(String str, Integer i, Bundle bundle){
        Log.w("MyLocation","Status Changed");
        this.endUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Determine whether new location is better than current best
        // estimate
        if (null == myLocation || location.getAccuracy() < myLocation.getAccuracy()) {
            myLocation = location;

            if (myLocation.getAccuracy() < MIN_ACCURACY) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
        }
    }

    private boolean servicesAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (ConnectionResult.SUCCESS == resultCode) {
            return true;
        }
        else {
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0).show();
            return false;
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
*/
