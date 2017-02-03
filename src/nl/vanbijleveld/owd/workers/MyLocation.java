package nl.vanbijleveld.owd.workers;

import android.location.Location;

public class MyLocation {

    static final Location getLocation() {
        Location loc = new Location("Mock");

        loc.setLatitude(52.386755);
        loc.setLongitude(5.301234);

        return loc;
    }
}
