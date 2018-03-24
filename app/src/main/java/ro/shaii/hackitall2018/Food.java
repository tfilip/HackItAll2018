package ro.shaii.hackitall2018;

/**
 * Created by filip on 24-Mar-18.
 */

public class Food {

    public String foodName;
    public String rest;
    public String productionDate;
    public String expiryDate;
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

    public Food(String foodName, String rest, String productionDate, String expiryDate, String descriere, String photoURL) {
        this.foodName = foodName;
        this.rest = rest;
        this.productionDate = productionDate;
        this.expiryDate = expiryDate;
        this.descriere = descriere;
        this.photoURL = photoURL;
    }



    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
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

    public String getRestaurant() {
        return this.rest;
    }

    public void setRestaurant(String Restaurant) {
        this.rest = Restaurant;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
