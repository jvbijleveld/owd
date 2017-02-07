package nl.vanbijleveld.owd.entities;

import android.location.Location;

public class OwdTask {

    private String requestor;
    private String message;
    private Location location;
    private Boolean getLocation;
    private ResponseLog responseLog;

    private TaskResponseType responseType;

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

    public Boolean getGetLocation() {
        return getLocation;
    }

    public void setGetLocation(Boolean getLocation) {
        this.getLocation = getLocation;
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
