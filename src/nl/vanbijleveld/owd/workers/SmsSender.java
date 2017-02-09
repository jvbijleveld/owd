package nl.vanbijleveld.owd.workers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import nl.vanbijleveld.owd.entities.OwdTask;
import nl.vanbijleveld.owd.entities.ResponseLog;
import nl.vanbijleveld.owd.entities.TaskResponseType;
import android.telephony.SmsManager;
import android.util.Log;

public class SmsSender {

    static void sendLocationResult(OwdTask task) {
        ResponseLog log = new ResponseLog();
        NumberFormat formatter = new DecimalFormat("#0.000000");
        String message = "";
        SmsManager smsManager = SmsManager.getDefault();

        message =
                "https://maps.google.com/maps?q=@" + formatter.format(task.getLocation().getLatitude()) + "," + formatter.format(task.getLocation().getLongitude())
                        + "&zoom=17  Speed: " + task.getLocation().getSpeed() + " acc:" + task.getLocation().getAccuracy() + "m";

        smsManager.sendTextMessage(task.getRequestor(), null, message, null, null);

        log.setDate(new Date());
        log.setReceiver(task.getRequestor());
        log.setType(TaskResponseType.SMS);
        log.setMessage(message);
        task.setResponseLog(log);

        Log.i("SmsSender", "Message: " + message + " -send to " + task.getRequestor());
    }

}
