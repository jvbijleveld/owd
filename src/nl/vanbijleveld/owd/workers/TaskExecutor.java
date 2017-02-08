package nl.vanbijleveld.owd.workers;

import nl.vanbijleveld.owd.entities.OwdTask;
import android.location.Location;
import android.util.Log;

public class TaskExecutor {

    // private static SimpleLocation mLocation;
    private OwdTask task;

    public void execute(OwdTask task) {
        Log.i("TaskExecutor", "Starting Task Executor");
        this.task = task;

        if (task.getFetchLocation()) {
            MyLocation location = new MyLocation(this);
            location.fetch();

            // mLocation = new SimpleLocation(task.getContext());
            // mLocation.beginUpdates();

            // mLocation.setListener(New LocationListener());

            // this.task.setLocation(mLocation.getPosition());

            // mLocation.endUpdates();

            // MyLocation myLoc = new MyLocation(task.getContext());
            // myLoc.setLocation(task);
            Log.i("TaskExecutor", "Fetch Location");
        }
        respond();
    }

    private boolean isCompleted() {
        if (task.getFetchLocation() && task.getLocation() != null) {
            return true;
        } else {
            return true;
        }
    }

    private void respond() {
        Log.i("TaskExecutor", "responding");
        if (this.isCompleted()) {
            switch (this.task.getResponseType()) {

            case SMS:
                Log.i("TaskExecutor:respond", "Sending SMS back");
                SmsSender.sendLocationResult(this.task);
                break;
            case PHONE:
                Log.i("TaskExecutor:respond", "Calling back");
                Dialer d = new Dialer();
                d.callme(task);
            default:
                break;
            }
        } else {
            Log.i("TaskExecutor", "Task not yet completed");
        }
    }

    public void updateLocation(Location location) {
        this.task.setLocation(location);
        respond();
    }

    public OwdTask getTask() {
        return this.task;
    }

}
