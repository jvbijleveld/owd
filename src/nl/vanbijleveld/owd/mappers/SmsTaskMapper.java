package nl.vanbijleveld.owd.mappers;

import nl.vanbijleveld.owd.entities.OwdTask;
import nl.vanbijleveld.owd.entities.TaskResponseType;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsTaskMapper {

    final static String triggerText = "WY@?";

    static public OwdTask map(SmsMessage message) {
        String messageBody = message.getDisplayMessageBody();

        if (triggerText.equals(messageBody.substring(0, triggerText.length()))) {

            Log.i("SmsTaskMapper", "Start processing message: " + message);
            OwdTask task = new OwdTask();
            task.setGetLocation(true);
            task.setRequestor(message.getDisplayOriginatingAddress());
            task.setResponseType(TaskResponseType.SMS);
            return task;

        } else {
            Log.i("SmsTaskMapper", "message not processed: " + message);
            return null;
        }

    }

}
