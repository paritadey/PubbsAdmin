package admin.pubbs.in.pubbsadminnew.List;
/*created by Parita Dey*/

public class UserProfileTripsList {
    private String arrival_time, booking_id;
    private int duration, amount;
    public UserProfileTripsList(){
    }
    public UserProfileTripsList(String booking_id, String arrival_time, int amount, int duration){
        this.booking_id = booking_id;
        this.arrival_time = arrival_time;
        this.amount = amount;
        this.duration = duration;
    }
    public void setArrivalTime(String arrival_time){
        this.arrival_time = arrival_time;
    }
    public String getArrivalTime(){
        return arrival_time;
    }
    public void setBookingId(String booking_id){
        this.booking_id = booking_id;
    }
    public String getBookingId(){
        return booking_id;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
    public int getDuration(){
        return duration;
    }
    public void setAmount(int amount){
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }
}
