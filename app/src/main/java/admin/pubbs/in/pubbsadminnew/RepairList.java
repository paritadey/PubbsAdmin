package admin.pubbs.in.pubbsadminnew;

/*created by Parita Dey*/

public class RepairList {
    private String bicycleId, dateTime;

    public RepairList() {
    }

    public RepairList(String bicycleId, String dateTime) {
        this.bicycleId = bicycleId;
        this.dateTime = dateTime;
    }

    public String getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(String bicycleId) {
        this.bicycleId = bicycleId;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public String getDateTime(){
        return dateTime;
    }
}
