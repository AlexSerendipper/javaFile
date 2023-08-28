package useSSM.pojo;

import java.sql.Date;

/**
 @author Alex
 @create 2023-07-22-16:58
 */


public class Holiday {
    int id;
    Date time;

    java.util.Date dateTime;

    public Holiday() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public java.util.Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(java.util.Date dateTime) {
        this.dateTime = dateTime;
    }
}
