package nl.vanbijleveld.owd.workers;

import nl.vanbijleveld.owd.entities.OwdTask;
import android.util.Log;

public class TaskExecutor {

    static public void execute(OwdTask task) {
        Log.i("TaskExecutor", "Starting Task Executor");

        if (task.getGetLocation()) {
            fetchLocation(task);
            Log.i("TaskExecutor", "Got Location");
        }

        respond(task);
    }

    private static void fetchLocation(OwdTask task) {
        task.setLocation(MyLocation.getLocation());
    }

    private static void respond(OwdTask task) {
        Log.i("TaskExecutor:respond", "Sending SMS back");
        switch (task.getResponseType()) {

        case SMS:
            SmsSender.sendLocationResult(task);
            break;
        default:
            break;
        }
    }
}
