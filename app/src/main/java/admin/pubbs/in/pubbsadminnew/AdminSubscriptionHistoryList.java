package admin.pubbs.in.pubbsadminnew;

public class AdminSubscriptionHistoryList {
    private String area_id, area_name, basic_plan_amount, basic_plan_month, basic_plan_mins;
    private String standard_plan_amount, standard_plan_month, standard_plan_mins, sweet_plan_amount, sweet_plan_month, sweet_plan_mins;
    private String premium_plan_amount, premium_plan_month, premium_plan_mins;

    public AdminSubscriptionHistoryList() {
    }

    public AdminSubscriptionHistoryList(String area_id, String area_name, String basic_plan_amount, String basic_plan_month,
                                        String basic_plan_mins, String standard_plan_amount, String standard_plan_month,
                                        String standard_plan_mins, String sweet_plan_amount, String sweet_plan_month, String sweet_plan_mins,
                                        String premium_plan_amount, String premium_plan_month, String premium_plan_mins) {
        this.area_id = area_id;
        this.area_name = area_name;
        this.basic_plan_amount = basic_plan_amount;
        this.basic_plan_month = basic_plan_month;
        this.basic_plan_mins = basic_plan_mins;
        this.standard_plan_amount = standard_plan_amount;
        this.standard_plan_month = standard_plan_month;
        this.standard_plan_mins = standard_plan_mins;
        this.sweet_plan_amount = sweet_plan_amount;
        this.sweet_plan_month = sweet_plan_month;
        this.sweet_plan_mins = sweet_plan_mins;
        this.premium_plan_amount = premium_plan_amount;
        this.premium_plan_month = premium_plan_month;
        this.premium_plan_mins = premium_plan_mins;

    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setBasic_plan_amount(String basic_plan_amount) {
        this.basic_plan_amount = basic_plan_amount;
    }

    public String getBasic_plan_amount() {
        return basic_plan_amount;
    }

    public void setBasic_plan_month(String basic_plan_month) {
        this.basic_plan_month = basic_plan_month;
    }

    public String getBasic_plan_month() {
        return basic_plan_month;
    }

    public String getBasic_plan_mins() {
        return basic_plan_mins;
    }
    public void setBasic_plan_mins(String basic_plan_mins){
        this.basic_plan_mins = basic_plan_mins;
    }

    public void setStandard_plan_amount(String standard_plan_amount) {
        this.standard_plan_amount = standard_plan_amount;
    }

    public String getStandard_plan_amount() {
        return standard_plan_amount;
    }

    public void setStandard_plan_month(String standard_plan_month) {
        this.standard_plan_month = standard_plan_month;
    }

    public String getStandard_plan_month() {
        return standard_plan_month;
    }

    public void setStandard_plan_mins(String standard_plan_mins) {
        this.standard_plan_mins = standard_plan_mins;
    }

    public String getStandard_plan_mins() {
        return standard_plan_mins;
    }

    public void setSweet_plan_amount(String sweet_plan_amount) {
        this.sweet_plan_amount = sweet_plan_amount;
    }

    public String getSweet_plan_amount() {
        return sweet_plan_amount;
    }

    public void setSweet_plan_month(String sweet_plan_month) {
        this.sweet_plan_month = sweet_plan_month;
    }

    public String getSweet_plan_month() {
        return sweet_plan_month;
    }

    public void setSweet_plan_mins(String sweet_plan_mins) {
        this.sweet_plan_mins = sweet_plan_mins;
    }

    public String getSweet_plan_mins() {
        return sweet_plan_mins;
    }

    public void setPremium_plan_amount(String premium_plan_amount) {
        this.premium_plan_amount = premium_plan_amount;
    }

    public String getPremium_plan_amount() {
        return premium_plan_amount;
    }

    public void setPremium_plan_month(String premium_plan_month) {
        this.premium_plan_month = premium_plan_month;
    }

    public String getPremium_plan_month() {
        return premium_plan_month;
    }

    public void setPremium_plan_mins(String premium_plan_mins) {
        this.premium_plan_mins = premium_plan_mins;
    }

    public String getPremium_plan_mins() {
        return premium_plan_mins;
    }
}

