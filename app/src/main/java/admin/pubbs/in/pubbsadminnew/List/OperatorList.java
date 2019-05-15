package admin.pubbs.in.pubbsadminnew.List;
/*created by Parita Dey*/

public class OperatorList {
    private String area_name, fullname, adminmobile, admin_type;
    int active;

    public OperatorList() {

    }

    public OperatorList(String fullname, String adminmobile, String area_name, String admin_type, int active) {
        this.fullname = fullname;
        this.adminmobile = adminmobile;
        this.area_name = area_name;
        this.admin_type = admin_type;
        this.active = active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    public int getActive() {
        return active;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
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
}
