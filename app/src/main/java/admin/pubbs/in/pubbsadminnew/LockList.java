package admin.pubbs.in.pubbsadminnew;
/*created by Parita Dey*/

public class LockList {
    private String lock_id, ble_id, lock_type, sim_number,totalQuantity, totalPrice, paymentMethod, date_time;
    public LockList(){}
    public LockList(String lock_id, String ble_id, String sim_number, String lock_type, String totalQuantity,
                    String totalPrice, String paymentMethod, String date_time){
        this.lock_id = lock_id;
        this.ble_id = ble_id;
        this.sim_number = sim_number;
        this.lock_type = lock_type;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.date_time = date_time;
    }

    public String getLock_id() {
        return lock_id;
    }

    public void setLock_id(String lock_id) {
        this.lock_id = lock_id;
    }

    public String getBle_id() {
        return ble_id;
    }

    public void setBle_id(String ble_id) {
        this.ble_id = ble_id;
    }

    public void setLock_type(String lock_type) {
        this.lock_type = lock_type;
    }

    public String getLock_type() {
        return lock_type;
    }

    public String getSim_number() {
        return sim_number;
    }

    public void setSim_number(String sim_number) {
        this.sim_number = sim_number;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getDate_time() {
        return date_time;
    }
}
