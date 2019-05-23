package admin.pubbs.in.pubbsadminnew.List;
/*created by Parita Dey*/

public class AdminList {
    private String full_name, email, adminmobile, date_time, area_name, area_id;
    public AdminList() {

    }
    public AdminList(String full_name, String email, String adminmobile,
                     String date_time, String area_name, String area_id) {
        this.full_name = full_name;
        this.email = email;
        this.adminmobile = adminmobile;
        this.date_time = date_time;
        this.area_name = area_name;
        this.area_id = area_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
}
