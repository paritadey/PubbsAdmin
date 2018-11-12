package admin.pubbs.in.pubbsadminnew;

/**
 * Created by LORD on 10/23/2016.
 */

public class VirtualStation {
    private String station_name,station_id,lat,lon;


    public VirtualStation(String station_name, String station_id, String lat, String lon) {
        this.station_name = station_name;
        this.station_id = station_id;
        this.lat = lat;
        this.lon = lon;
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


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

}
