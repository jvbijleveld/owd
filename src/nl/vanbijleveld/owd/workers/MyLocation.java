package nl.vanbijleveld.owd.workers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MyLocation {

    private final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    private final Integer LOCATION_INTERVAL = 10 * 1000;

    private final TaskExecutor executor;
    private final LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location myLocation;

    public MyLocation(TaskExecutor executor) {
        this.executor = executor;
        mLocationManager = (LocationManager) this.executor.getTask().getContext().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    }

    public void fetch() {
        if (myLocation != null) {
            executor.updateLocation(myLocation);
            endUpdates();
        } else {
            mLocationListener = createLocationListener();
            mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, LOCATION_INTERVAL, 0, mLocationListener);
        }
    }

    public void endUpdates() {
        if (mLocationListener != null) {
            mLocationManager.removeUpdates(mLocationListener);
            mLocationListener = null;
        }
    }

    private LocationListener createLocationListener() {
        return new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                myLocation = location;
                executor.updateLocation(location);
                endUpdates();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
            }

        };
    }
}
