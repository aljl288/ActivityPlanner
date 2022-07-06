package ukm.com.academiccalendar.Model;

public class Events{
    private String ename, description, eventdate, image, contact, time, date, eid;

    public Events()
    {
    }

    public Events(String ename, String description, String eventdate, String image, String contact, String time, String date, String eid) {
        this.ename = ename;
        this.description = description;
        this.eventdate = eventdate;
        this.image = image;
        this.contact = contact;
        this.time = time;
        this.date = date;
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getEid() {
        return eid;
    }

    public void setPid(String eid) {
        this.eid = eid;
    }
}
