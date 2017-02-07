package nl.vanbijleveld.owd.workers;

import nl.vanbijleveld.owd.entities.OwdTask;
import android.util.Log;

public class TaskExecutor {

    static public void execute(OwdTask task) {
        Log.i("TaskExecutor", "Starting Task Executor");

        if (task.getGetLocation()) {
            task.setLocation(MyLocation.getLocation());
            Log.i("TaskExecutor", "Got Location");
        }

        respond(task);
    }

    private static void respond(OwdTask task) {

        switch (task.getResponseType()) {

        case SMS:
            Log.i("TaskExecutor:respond", "Sending SMS back");
            SmsSender.sendLocationResult(task);
            break;
        case PHONE:
            Log.i("TaskExecutor:respond", "Calling back");
            Dialer d = new Dialer();
            d.callme(task);
        default:
            break;
        }
    }
}
