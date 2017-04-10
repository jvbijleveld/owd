package nl.vanbijleveld.owd.workers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by Jeffrey on 10-4-2017.
 */

public class Permissions {

    private Activity activity;

    public Permissions(Activity activity) {
        this.activity = activity;
    }

    public boolean askForSms(){
        return promptForPermission(Manifest.permission.READ_SMS);
    }

    public boolean askForLocation(){
        return promptForPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    private boolean promptForPermission(String permission){
        if (ContextCompat.checkSelfPermission(this.activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.activity, new String[]{permission}, 1);
        }
        Log.i("Permissions", "Asked for " + permission );
        return ContextCompat.checkSelfPermission(this.activity, permission) == PackageManager.PERMISSION_GRANTED ? true : false;
    }

}
