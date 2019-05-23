package admin.pubbs.in.pubbsadminnew.List;

public class SupportUserChatList {
    private String sender, email, message, date_time, area_id;
    public SupportUserChatList(){}
    public SupportUserChatList(String sender, String email, String message, String date_time, String area_id){
        this.sender = sender;
        this.email = email;
        this.message = message;
        this.date_time = date_time;
        this.area_id = area_id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_id() {
        return area_id;
    }
}
