package admin.pubbs.in.pubbsadminnew.List;
/*created by Parita Dey*/

public class SubadminList {
    private String full_name, admin_mobile, area_id;
    public SubadminList(){}
    public SubadminList(String full_name, String admin_mobile, String area_id){
        this.full_name = full_name;
        this.admin_mobile = admin_mobile;
        this.area_id = area_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAdmin_mobile() {
        return admin_mobile;
    }

    public void setAdmin_mobile(String admin_mobile) {
        this.admin_mobile = admin_mobile;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }
}
