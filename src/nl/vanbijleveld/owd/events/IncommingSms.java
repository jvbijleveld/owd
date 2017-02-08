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

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);

                    TaskExecutor executor = new TaskExecutor();

                    OwdTask task = SmsTaskMapper.map(currentMessage);

                    if (task != null) {
                        task.setContext(context);
                        try {
                            executor.execute(task);
                        } catch (Exception e) {
                            Log.e("TaskExector", "Error Executing task; " + e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver " + e);
        }
    }
}
