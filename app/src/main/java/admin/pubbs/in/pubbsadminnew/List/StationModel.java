package admin.pubbs.in.pubbsadminnew.List;

import com.google.android.gms.maps.model.LatLng;

public class StationModel {
    private String id;
    private String name;
    private LatLng location;
    private int available;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
