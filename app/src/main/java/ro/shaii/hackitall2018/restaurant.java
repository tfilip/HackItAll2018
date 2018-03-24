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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
