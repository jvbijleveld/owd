package nl.vanbijleveld.owd.workers;

/**
 * Created by Jeffrey on 19-4-2017.
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import android.util.Log;

public class FusedLocation implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final TaskExecutor executor;
    private Context context;

    private static final long ONE_MIN = 1000 * 60;
    private static final long TWO_MIN = ONE_MIN * 2;
    private static final long FIVE_MIN = ONE_MIN * 5;
    private static final long POLLING_FREQ = 1000 * 30;
    private static final long FASTEST_UPDATE_FREQ = 1000 * 5;
    private static final float MIN_ACCURACY = 25.0f;
    private static final float MIN_LAST_READ_ACCURACY = 500.0f;

    private LocationRequest mLocationRequest;
    private Location mBestReading;

    private GoogleApiClient mGoogleApiClient;

    public FusedLocation(TaskExecutor executor)  {
        this.executor = executor;
        this.context = this.executor.getTask().getContext();

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(POLLING_FREQ);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_FREQ);

        mGoogleApiClient = new GoogleApiClient.Builder(this.context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void fetch() {
        Log.i("FusedLocation","Start fetching");
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("FusedLocation","Location has changed");
        if (null == mBestReading || location.getAccuracy() < mBestReading.getAccuracy()) {
            mBestReading = location;

            if (mBestReading.getAccuracy() < MIN_ACCURACY) {
                Log.i("FusedLocation","Got accurate location, updating task");
                executor.updateLocation(mBestReading);
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
        }
    }

    @Override
    public void onConnected(Bundle dataBundle) {
        Log.i("FusedLocation","Connected to Google API");

        int hasLocationPermission = ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION);
        Log.i("FusedLocation","Connected, check current Location");
        mBestReading = bestLastKnownLocation(MIN_LAST_READ_ACCURACY, FIVE_MIN);

            if (null == mBestReading
                    || mBestReading.getAccuracy() > MIN_LAST_READ_ACCURACY
                    || mBestReading.getTime() < System.currentTimeMillis() - TWO_MIN) {

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            }else{
                Log.i("FusedLocation","Request new location");
                executor.updateLocation(mBestReading);
            }
      }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private Location bestLastKnownLocation(float minAccuracy, long minTime) {
        Location bestResult = null;
        float bestAccuracy = Float.MAX_VALUE;
        long bestTime = Long.MIN_VALUE;

        int hasLocationPermission = ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
            Log.i("FusedLocation","bestLastKnownLocation, has permission");
            Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mCurrentLocation != null) {
                float accuracy = mCurrentLocation.getAccuracy();
                long time = mCurrentLocation.getTime();

                if (accuracy < bestAccuracy) {
                    bestResult = mCurrentLocation;
                    bestAccuracy = accuracy;
                    bestTime = time;
                }
            }
            Log.i("FusedLocation","return bestLastKnownLocation");
            if (bestAccuracy > minAccuracy || bestTime < minTime) {
                return null;
            } else {
                return bestResult;
            }
        }else{
            Log.i("FusedLocation","bestLastKnownLocation, has NO permission");
            return null;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.w("FusedLocation","Connection failed");
    }

    private boolean servicesAvailable() {
        int hasLocationPermission = ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }
}
