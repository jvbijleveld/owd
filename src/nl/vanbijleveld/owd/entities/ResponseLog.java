package nl.vanbijleveld.owd.entities;

import java.util.Date;

public class ResponseLog {

    private Date date;
    private TaskResponseType type;
    private String receiver;
    private String message;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TaskResponseType getType() {
        return type;
    }

    public void setType(TaskResponseType type) {
        this.type = type;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
