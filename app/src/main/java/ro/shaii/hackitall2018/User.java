package ro.shaii.hackitall2018;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by filip on 24-Mar-18.
 */

public class User {

    public String address;
    public String type;
    public ArrayList<String> restaurante;


    public User(String address,String type) {
        this.address = address;
        this.type = type;
        this.restaurante = new ArrayList<>();
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
