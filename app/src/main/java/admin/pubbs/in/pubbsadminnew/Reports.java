package admin.pubbs.in.pubbsadminnew;

/**
 * Created by LORD on 4/9/2018.
 */

public class Reports {

    private String userid,message,dateTime;

    public Reports(String userid, String message,String dateTime) {
        this.userid = userid;
        this.message = message;
        this.dateTime=dateTime;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
