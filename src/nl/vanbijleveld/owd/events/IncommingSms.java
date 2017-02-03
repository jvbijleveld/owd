package nl.vanbijleveld.owd.events;

import nl.vanbijleveld.owd.entities.OwdTask;
import nl.vanbijleveld.owd.mappers.SmsTaskMapper;
import nl.vanbijleveld.owd.workers.TaskExecutor;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class IncommingSms extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    OwdTask task = SmsTaskMapper.map(currentMessage);
                    TaskExecutor.execute(task);

                    /*
                     * String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                     * 
                     * String senderNum = phoneNumber; String message = currentMessage.getDisplayMessageBody();
                     * 
                     * Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                     * 
                     * // Show Alert int duration = Toast.LENGTH_LONG; Toast toast = Toast.makeText(context,
                     * "senderNum: " + senderNum + ", message: " + message, duration); toast.show();
                     */
                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }
}
