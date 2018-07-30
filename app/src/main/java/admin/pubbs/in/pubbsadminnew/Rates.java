package admin.pubbs.in.pubbsadminnew;

/**
 * Created by LORD on 9/17/2017.
 */

public class Rates {
    private int id;
    private String duration,rate;

    public Rates(int id, String duration, String rate) {
        this.id = id;
        this.duration = duration;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
