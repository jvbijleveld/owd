package nl.vanbijleveld.owd.mappers;

import nl.vanbijleveld.owd.entities.OwdTask;
import nl.vanbijleveld.owd.entities.TaskResponseType;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsTaskMapper {

    final static String triggerLocationText = "WY@?";

    static public OwdTask map(SmsMessage message) {
        String messageBody = message.getDisplayMessageBody();

        if (triggerLocationText.equals(messageBody.substring(0, triggerLocationText.length()))) {
            OwdTask task = new OwdTask();

            Log.i("SmsTaskMapper", "Start processing message: " + message);
            task.setRequestor(message.getDisplayOriginatingAddress());

            if (messageBody.contains(" -call")) {
                task.setResponseType(TaskResponseType.PHONE);
            } else {
                task.setFetchLocation(true);
                task.setResponseType(TaskResponseType.SMS);
            }
            return task;

        } else {
            Log.i("SmsTaskMapper", "message not processed: " + message);
            return null;
        }

    }

}
