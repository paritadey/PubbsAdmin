package admin.pubbs.in.pubbsadminnew;
/*created by Parita Dey*/

public class UserProfileTripsList {
    private String dateTime, bicycleId, timeStamp, money;
    public UserProfileTripsList(){
    }
    public UserProfileTripsList(String dateTime, String bicycleId, String timeStamp, String money){
        this.dateTime = dateTime;
        this.bicycleId = bicycleId;
        this.timeStamp = timeStamp;
        this.money = money;
    }
    public void setDateTime(String dateTime){
        this.dateTime = dateTime;
    }
    public String getDateTime(){
        return dateTime;
    }
    public void setBicycleId(String bicycleId){
        this.bicycleId = bicycleId;
    }
    public String getBicycleId(){
        return bicycleId;
    }
    public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }
    public String getTimeStamp(){
        return timeStamp;
    }
    public void setMoney(String money){
        this.money = money;
    }
    public String getMoney() {
        return money;
    }
}
