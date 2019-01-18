package admin.pubbs.in.pubbsadminnew;
/*created by Parita Dey*/

public class LockDetailsList {
    private String area_id, orderNumber,lock_id, ble_id, sim_number, lock_type, quantity, price, payment_method;
    public LockDetailsList(){}
    public LockDetailsList(String orderNumber, String area_id){
        this.orderNumber = orderNumber;
        this.area_id = area_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber){
        this.orderNumber = orderNumber;
    }

    public String getLock_id() {
        return lock_id;
    }
    public void setLock_id(String lock_id){
        this.lock_id = lock_id;
    }

    public String getBle_id() {
        return ble_id;
    }
    public void setBle_id(String ble_id){
        this.ble_id = ble_id;
    }

    public String getSim_number() {
        return sim_number;
    }
    public void setSim_number(String sim_number){
        this.sim_number = sim_number;
    }

    public String getLock_type() {
        return lock_type;
    }

    public void setLock_type(String lock_type) {
        this.lock_type = lock_type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
