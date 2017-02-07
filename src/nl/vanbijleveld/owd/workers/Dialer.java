package nl.vanbijleveld.owd.workers;

import nl.vanbijleveld.owd.entities.OwdTask;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class Dialer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Dialer() {

    }

    public void callme(OwdTask task) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + task.getRequestor()));
            startActivity(intent);
        } catch (Exception e) {
            Log.e("Dialer", "Error in callme " + e);
        }
    }

}
