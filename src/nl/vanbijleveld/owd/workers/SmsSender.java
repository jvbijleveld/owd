package nl.vanbijleveld.owd.workers;

import nl.vanbijleveld.owd.entities.OwdTask;
import android.telephony.SmsManager;
import android.util.Log;

public class SmsSender {

    static void sendLocationResult(OwdTask task) {
        String message = "";
        SmsManager smsManager = SmsManager.getDefault();

        message = "Im At: lat:" + task.getLocation().getLatitude() + ", lon:" + task.getLocation().getLongitude();

        smsManager.sendTextMessage(task.getRequestor(), null, message, null, null);
        Log.i("SmsSender", "Message: " + message + " -send to " + task.getRequestor());
    }

}
