package admin.pubbs.in.pubbsadminnew;
/*created by Parita Dey*/

public class RechargeBatteryList {
    String cycle_id, battery_status;

    public RechargeBatteryList() {

    }

    public RechargeBatteryList(String cycle_id, String battery_status) {
        this.cycle_id = cycle_id;
        this.battery_status = battery_status;
    }

    public void setCycle_id(String cycle_id) {
        this.cycle_id = cycle_id;
    }

    public String getCycle_id() {
        return cycle_id;
    }

    public void setBattery_status(String battery_status) {
        this.battery_status = battery_status;
    }

    public String getBattery_status() {
        return battery_status;
    }
}
