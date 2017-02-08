package nl.vanbijleveld.owd.workers;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

public class MyLocation implements ConnectionCallbacks, OnConnectionFailedListener {

    private static GoogleApiClient mGoogleApiCLient;
    private final TaskExecutor executor;

    public MyLocation(TaskExecutor executor) {
        this.executor = executor;
        mGoogleApiCLient =
                new GoogleApiClient.Builder(this.executor.getTask().getContext()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API)
                .build();
    }

    public void fetch() {
        mGoogleApiCLient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        mGoogleApiCLient.disconnect();
    }

    @Override
    public void onConnected(Bundle arg0) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiCLient);

        Log.i("MyLocation", "Fetched location");

        if (location != null && this.executor != null) {
            this.executor.updateLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiCLient.disconnect();
    }

}
