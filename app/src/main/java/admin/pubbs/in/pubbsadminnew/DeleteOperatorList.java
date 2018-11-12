package admin.pubbs.in.pubbsadminnew;

public class DeleteOperatorList {
    private String area_name, fullname, adminmobile, admin_type;

    public DeleteOperatorList() {

    }
    public DeleteOperatorList(String fullname, String area_name, String adminmobile, String admin_type) {
        this.fullname = fullname;
        this.area_name = area_name;
        this.adminmobile = adminmobile;
        this.admin_type = admin_type;
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
