package admin.pubbs.in.pubbsadminnew;

/*created by Parita Dey*/

public class RepairList {
    private String bicycleId;
    public RepairList(){

    }
    public RepairList(String bicycleId){
        this.bicycleId = bicycleId;
    }
    public String getBicycleId(){
        return bicycleId;
    }
    public void setBicycleId(String bicycleId){
        this.bicycleId = bicycleId;
    }
}
