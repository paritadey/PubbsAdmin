package admin.pubbs.in.pubbsadminnew;

/**
 * Created by MAC-200 on 7/8/2016.
 */
public class AreaPojo {
    private int id;
    private String name;
    private String lat;
    private String lon;

    public AreaPojo(int id,String name, String lat, String lon) {
        this.id=id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public AreaPojo(int id,String name) {
        this.id=id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
