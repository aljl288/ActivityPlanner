package ukm.com.academiccalendar.Model;

public class Feedbacks {
    private String fid, fname, fdescription, fcontactnumber, feventname, date, time;

    public Feedbacks()
    {
    }

    public Feedbacks(String fid, String fname, String fdescription, String fcontactnumber, String feventname,String date, String time) {
        this.fid = fid;
        this.fname = fname;
        this.fdescription = fdescription;
        this.fcontactnumber = fcontactnumber;
        this.feventname = feventname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFdescription() {
        return fdescription;
    }

    public void setFdescription(String fdescription) {
        this.fdescription = fdescription;
    }

    public String getFcontactnumber() {
        return fcontactnumber;
    }

    public void setFcontactnumber(String fcontactnumber) {
        this.fcontactnumber = fcontactnumber;
    }

    public String getFeventname() {
        return feventname;
    }

    public void setFeventname(String feventname) {
        this.feventname = feventname;
    }
}
