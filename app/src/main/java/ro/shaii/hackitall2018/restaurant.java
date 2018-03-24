package ro.shaii.hackitall2018;

import java.util.ArrayList;

/**
 * Created by chris on 24.03.2018.
 */

public class restaurant extends User{

    public int rating;
    public ArrayList<Review> reviews;
    public String telefon;
    public String nume;

    public restaurant(String address, String telefon,String nume) {
        super.type = "Restaurant";
        super.address = address;
        this.rating = 0;
        this.telefon = telefon;
        this.nume = nume;
    }

    public restaurant(){

    }


    public String getNume() {
        return nume;
    }
}
