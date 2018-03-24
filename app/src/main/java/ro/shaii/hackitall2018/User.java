package ro.shaii.hackitall2018;

/**
 * Created by filip on 24-Mar-18.
 */

public class User {

    public String address;
    public String type;

    public User(String address,String type) {
        this.address = address;
        this.type = type;
    }

    public User(){

    };

    public void setAsClient(){
        this.type = "Client";
    }

    public void  setAsRestaurant(){
        this.type = "Restaurant";
    }


    public String getType() {
        return type;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }
}
