package ro.shaii.hackitall2018;

import java.util.Date;

/**
 * Created by filip on 24-Mar-18.
 */

public class Food {

    public String foodName;
    public restaurant rest;
    public Date productionDate;
    public Date expirtyDate;
    public String descriere;
    public String photoURL;


    public Food(){

    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Food(String foodName, restaurant rest, Date productionDate, Date expirtyDate, String descriere, String photoURL) {
        this.foodName = foodName;
        this.rest = rest;
        this.productionDate = productionDate;
        this.expirtyDate = expirtyDate;
        this.descriere = descriere;
        this.photoURL = photoURL;
    }

    public Food(String foodName, restaurant restaurant, Date productionDate, Date expirtyDate, String descriere) {
        this.foodName = foodName;
        this.rest = restaurant;
        this.productionDate = productionDate;
        this.expirtyDate = expirtyDate;
        this.descriere = descriere;
    }

    public restaurant getRest() {
        return rest;
    }

    public void setRest(restaurant rest) {
        this.rest = rest;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public restaurant getRestaurant() {
        return this.rest;
    }

    public void setRestaurant(restaurant Restaurant) {
        this.rest = Restaurant;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpirtyDate() {
        return expirtyDate;
    }

    public void setExpirtyDate(Date expirtyDate) {
        this.expirtyDate = expirtyDate;
    }
}
