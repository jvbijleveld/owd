package nl.vanbijleveld.owd.workers;

import java.util.Date;

import nl.vanbijleveld.owd.entities.OwdTask;
import nl.vanbijleveld.owd.entities.ResponseLog;
import nl.vanbijleveld.owd.entities.TaskResponseType;
import android.telephony.SmsManager;
import android.util.Log;

public class SmsSender {

    static void sendLocationResult(OwdTask task) {
        ResponseLog log = new ResponseLog();
        String message = "";
        SmsManager smsManager = SmsManager.getDefault();

        message = "Im At: maps.google.com/maps?q=@" + task.getLocation().getLatitude() + "," + task.getLocation().getLongitude() + "&zoom=17";

        smsManager.sendTextMessage(task.getRequestor(), null, message, null, null);

        log.setDate(new Date());
        log.setReceiver(task.getRequestor());
        log.setType(TaskResponseType.SMS);
        log.setMessage(message);
        task.setResponseLog(log);

        Log.i("SmsSender", "Message: " + message + " -send to " + task.getRequestor());
    }

}
