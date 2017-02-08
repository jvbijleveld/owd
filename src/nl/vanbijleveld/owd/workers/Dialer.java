package nl.vanbijleveld.owd.workers;

import nl.vanbijleveld.owd.entities.OwdTask;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class Dialer {

    public Dialer() {

    }

    public void callme(OwdTask task) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + task.getRequestor()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            task.getContext().startActivity(intent);
        } catch (Exception e) {
            Log.e("Dialer", "Error in callme " + e);
        }
    }

}
