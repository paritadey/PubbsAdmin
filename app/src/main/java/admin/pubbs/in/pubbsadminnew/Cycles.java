package admin.pubbs.in.pubbsadminnew;

/**
 * Created by LORD on 8/30/2017.
 */

public class Cycles {

    private String cycleId,cycleAddress;
    int batteryStatus;

    public Cycles(String cycleId, String cycleAddress, int batteryStatus) {
        this.cycleId = cycleId;
        this.cycleAddress = cycleAddress;
        this.batteryStatus = batteryStatus;
    }

    public String getCycleId() {
        return cycleId;
    }

    public void setCycleId(String cycleId) {
        this.cycleId = cycleId;
    }

    public String getCycleAddress() {
        return cycleAddress;
    }

    public void setCycleAddress(String cycleAddress) {
        this.cycleAddress = cycleAddress;
    }

    public int getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(int batteryStatus) {
        this.batteryStatus = batteryStatus;
    }
}
