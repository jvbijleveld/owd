package nl.vanbijleveld.owd.entities;

import android.content.Context;
import android.location.Location;

public class OwdTask {

    private Context context;
    private String requestor;
    private String message;
    private Location location;
    private boolean fetchLocation;
    private ResponseLog responseLog;

    private TaskResponseType responseType;

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean getFetchLocation() {
        return fetchLocation;
    }

    public void setFetchLocation(boolean fetchLocation) {
        this.fetchLocation = fetchLocation;
    }

    public TaskResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(TaskResponseType responseType) {
        this.responseType = responseType;
    }

    public ResponseLog getResponseLog() {
        return this.responseLog;
    }

    public void setResponseLog(ResponseLog responseLog) {
        this.responseLog = responseLog;
    }

}
