package nl.vanbijleveld.owd.workers;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import android.content.pm.PackageManager;


import com.google.android.gms.common.api.GoogleApiClient;

public class MyLocation implements ConnectionCallbacks, OnConnectionFailedListener {

    private final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    private final Integer LOCATION_INTERVAL = 10 * 1000;

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
        //mLocationManager = (LocationManager) this.context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        int hasLocationPermission = ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
            myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            executor.updateLocation(myLocation);
            this.endUpdates();
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
