package admin.pubbs.in.pubbsadminnew;

public class UserList {
    private String userName, userPhone, userId, userImei;
    public UserList(){

    }
    public UserList(String userName, String userPhone, String userId, String userImei){
        this.userName = userName;
        this.userPhone = userPhone;
        this.userId = userId;
        this.userImei = userImei;
    }
    public String getUserName(){return userName;}
    public void setUserName(String userName){
        this.userName=userName;
    }
    public String getUserPhone(){return  userPhone;}
    public void setUserPhone(String userPhone){
        this.userPhone=userPhone;
    }
    public String getUserId(){return userId;}
    public void setUserId(String userId){
        this.userId=userId;
    }
    public String getUserImei(){return userImei;}
    public void setUserImei(String userImei){this.userImei=userImei;}
}
