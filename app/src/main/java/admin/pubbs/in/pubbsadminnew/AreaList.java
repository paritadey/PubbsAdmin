package admin.pubbs.in.pubbsadminnew;
/*created by Parita Dey*/

public class AreaList {
    private String areaId, areaName, areaLatLon;
    public AreaList(){

    }
    public AreaList(String areaId, String areaName, String areaLatLon){
        this.areaId=areaId;
        this.areaName=areaName;
        this.areaLatLon=areaLatLon;
    }
    public String getAreaId(){
        return areaId;
    }
    public void setAreaId(String areaId){
        this.areaId=areaId;
    }
    public String getAreaName(){
        return areaName;
    }
    public void setAreaName(String areaName){
        this.areaName=areaName;
    }
    public String getAreaLatLon(){
        return areaLatLon;
    }
    public void setAreaLatLon(String areaLatLon){
        this.areaLatLon=areaLatLon;
    }
}
