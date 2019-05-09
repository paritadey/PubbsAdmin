package admin.pubbs.in.pubbsadminnew.List;
/*created by Parita Dey*/

public class DeleteStationList {
    private String station_id, station_name, area_name, area_id;

    public DeleteStationList() {

    }
    public DeleteStationList(String station_name, String station_id, String area_name, String area_id) {
        this.station_name = station_name;
        this.station_id = station_id;
        this.area_name = area_name;
        this.area_id = area_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setArea_name(String area_name){ this.area_name = area_name; }

    public String getArea_name(){ return area_name; }

    public void setArea_id(String area_id){ this.area_id = area_id; }

    public String getArea_id(){ return area_id; }

}
