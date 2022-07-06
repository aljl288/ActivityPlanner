package ukm.com.academiccalendar.Model;

public class Applicant{
    private String applicationEvent,applicationName, applicationMatric, applicationDescription, applicationeventdate, status, eventstatus, time ,date, aid;

    public Applicant()
    {
    }



    public Applicant(String applicationEvent, String applicationName, String applicationMatric, String applicationeventdate, String applicationDescription, String status, String eventstatus, String time, String date, String aid) {
        this.applicationEvent = applicationEvent;
        this.applicationName = applicationName;
        this.applicationMatric = applicationMatric;
        this.applicationDescription = applicationDescription;
        this.status = status;
        this.eventstatus = eventstatus;
        this.time = time;
        this.date = date;
        this.aid = aid;
        this.applicationeventdate = applicationeventdate;
    }

    public String getApplicationeventdate() {
        return applicationeventdate;
    }

    public void setApplicationeventdate(String applicationeventdate) {
        this.applicationeventdate = applicationeventdate;
    }

    public String getEventstatus() {
        return eventstatus;
    }

    public void setEventstatus(String eventstatus) {
        this.eventstatus = eventstatus;
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
