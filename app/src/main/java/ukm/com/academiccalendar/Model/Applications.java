package ukm.com.academiccalendar.Model;

public class Applications{
 private String applicationEvent,applicationName, applicationMatric, applicationDescription, status, time ,date, aid;

    public Applications()
    {
    }

    public Applications(String applicationEvent, String applicationName, String applicationMatric, String applicationDescription, String status, String time, String date, String aid) {
        this.applicationEvent = applicationEvent;
        this.applicationName = applicationName;
        this.applicationMatric = applicationMatric;
        this.applicationDescription = applicationDescription;
        this.status = status;
        this.time = time;
        this.date = date;
        this.aid = aid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApplicationEvent() {
        return applicationEvent;
    }

    public void setApplicationEvent(String applicationEvent) {
        this.applicationEvent = applicationEvent;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationMatric() {
        return applicationMatric;
    }

    public void setApplicationMatric(String applicationMatric) {
        this.applicationMatric = applicationMatric;
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
