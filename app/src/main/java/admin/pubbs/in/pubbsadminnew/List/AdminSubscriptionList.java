package admin.pubbs.in.pubbsadminnew.List;
/*created by Parita Dey*/

public class AdminSubscriptionList {
    private String area_id, admin_mobile, email;
    public AdminSubscriptionList(){}
    public AdminSubscriptionList(String area_id, String admin_mobile, String email){
        this.area_id = area_id;
        this.admin_mobile = admin_mobile;
        this.email = email;
    }

    public String getArea_id(){return area_id;}

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getAdminmobile() {
        return admin_mobile;
    }

    public void setAdminmobile(String admin_mobile) {
        this.admin_mobile = admin_mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
