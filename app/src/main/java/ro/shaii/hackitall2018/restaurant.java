package ro.shaii.hackitall2018;

import java.util.ArrayList;

/**
 * Created by chris on 24.03.2018.
 */

public class restaurant extends User{

    private String nume_restaurant;
    private int rating;
    private ArrayList<Review> reviews;
    private int telefon;

    public restaurant(String nume_restaurant, String address, int telefon) {
        super.type = "Restaurant";
        super.address = address;
        this.nume_restaurant = nume_restaurant;
        this.rating = 0;
        this.telefon = telefon;
    }

    public restaurant(){

    }

}
