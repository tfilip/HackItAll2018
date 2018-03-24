package ro.shaii.hackitall2018;

import java.util.ArrayList;

/**
 * Created by chris on 24.03.2018.
 */

public class restaurant extends User{

    private int rating;
    private ArrayList<Review> reviews;
    private String telefon;

    public restaurant(String address, String telefon) {
        super.type = "Restaurant";
        super.address = address;
        this.rating = 0;
        this.telefon = telefon;
    }

    public restaurant(){

    }

}
