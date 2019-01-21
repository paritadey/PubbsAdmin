package admin.pubbs.in.pubbsadminnew;
/*created by Parita Dey*/

public class AdminSubscriptionHistoryList {
    private String subscriptionPlanName, startDate, endDate, description;
    private int rideNumber, rideTime, money, carryForward;

    public AdminSubscriptionHistoryList() {
    }

    public AdminSubscriptionHistoryList(String subscriptionPlanName, String startDate, String endDate, int rideNumber,
                                        int rideTime, int money, String description, int carryForward) {
        this.subscriptionPlanName = subscriptionPlanName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rideNumber = rideNumber;
        this.rideTime = rideTime;
        this.money = money;
        this.description = description;
        this.carryForward = carryForward;
    }

    public String getSubscriptionPlanName() {
        return subscriptionPlanName;
    }

    public void setSubscriptionPlanName(String subscriptionPlanName) {
        this.subscriptionPlanName = subscriptionPlanName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getRideNumber() {
        return rideNumber;
    }

    public void setRideNumber(int rideNumber) {
        this.rideNumber = rideNumber;
    }

    public int getRideTime() {
        return rideTime;
    }

    public void setRideTime(int rideTime) {
        this.rideTime = rideTime;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCarryForward() {
        return carryForward;
    }

    public void setCarryForward(int carryForward) {
        this.carryForward = carryForward;
    }
}

