package admin.pubbs.in.pubbsadminnew;

/**
 * Created by LORD on 10/23/2016.
 */

public class Stations {
    private String station_name,station_id,area,lat,lon,rp;
    boolean virtual;
    private int available;

    public Stations(String station_name, String station_id, String area, String lat, String lon, String rp,boolean virtual,int available) {
        this.station_name = station_name;
        this.station_id = station_id;
        this.area = area;
        this.lat = lat;
        this.lon = lon;
        this.rp=rp;
        this.virtual=virtual;
        this.available=available;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getRp() {
        return rp;
    }

    public void setRp(String rp) {
        this.rp = rp;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
