package admin.pubbs.in.pubbsadminnew;

public class StationList {
    private String full_name, adminmobile, station_name, station_id, area_name;
    public StationList(){}
    public StationList(String full_name, String adminmobile, String station_name, String station_id, String area_name){
        this.full_name = full_name;
        this.adminmobile = adminmobile;
        this.station_name = station_name;
        this.station_id = station_id;
        this.area_name =area_name;
    }
    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAdminmobile() {
        return adminmobile;
    }

    public void setAdminmobile(String adminmobile) {
        this.adminmobile = adminmobile;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
}
