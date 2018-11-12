package admin.pubbs.in.pubbsadminnew;

public class Feedbacklist {
    private String adminmobile, admin_type, subject, message, date_time, email;
    public Feedbacklist(){}
    public Feedbacklist(String adminmobile, String admin_type, String subject, String message, String date_time, String email){
        this.adminmobile = adminmobile;
        this.admin_type = admin_type;
        this.subject = subject;
        this.message = message;
        this.date_time = date_time;
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminmobile() {
        return adminmobile;
    }

    public void setAdminmobile(String adminmobile) {
        this.adminmobile = adminmobile;
    }

    public String getAdmin_type() {
        return admin_type;
    }

    public void setAdmin_type(String admin_type) {
        this.admin_type = admin_type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getDate_time() {
        return date_time;
    }
}
