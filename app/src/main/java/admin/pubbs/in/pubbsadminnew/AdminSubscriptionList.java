package admin.pubbs.in.pubbsadminnew;

public class AdminSubscriptionList {
    private String adminmobile, email;
    public AdminSubscriptionList(){}
    public AdminSubscriptionList(String adminmobile, String email){
        this.adminmobile = adminmobile;
        this.email = email;
    }

    public String getAdminmobile() {
        return adminmobile;
    }

    public void setAdminmobile(String adminmobile) {
        this.adminmobile = adminmobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
