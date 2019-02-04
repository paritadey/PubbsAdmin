package admin.pubbs.in.pubbsadminnew;

public class UserProfilePlansList {
    String subscription_name, subscription_id, date_time;
    int time_limit, amount;

    public UserProfilePlansList() {
    }

    public UserProfilePlansList(String subscription_name, String subscription_id, String date_time, int amount, int time_limit) {
        this.subscription_name = subscription_name;
        this.subscription_id = subscription_id;
        this.date_time = date_time;
        this.amount = amount;
        this.time_limit = time_limit;
    }

    public String getSubscription_name() {
        return subscription_name;
    }

    public void setSubscription_name(String subscription_name) {
        this.subscription_name = subscription_name;
    }

    public String getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(String subscription_id) {
        this.subscription_id = subscription_id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(int time_limit) {
        this.time_limit = time_limit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
