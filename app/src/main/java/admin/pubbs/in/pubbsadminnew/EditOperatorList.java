package admin.pubbs.in.pubbsadminnew;

public class EditOperatorList {
    private String full_name, admin_mobile, admin_type;
    int active;

    public EditOperatorList() {

    }

    public EditOperatorList(String admin_mobile, String full_name, String admin_type, int active) {
        this.admin_mobile = admin_mobile;
        this.full_name = full_name;
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
        return full_name;
    }

    public void setFullname(String fullname) {
        this.full_name = fullname;
    }

    public String getAdminmobile() {
        return admin_mobile;
    }

    public void setAdminmobile(String admin_mobile) {
        this.admin_mobile = admin_mobile;
    }

    public String getAdmin_type() {
        return admin_type;
    }

    public void setAdmin_type(String admin_type) {
        this.admin_type = admin_type;
    }

}
