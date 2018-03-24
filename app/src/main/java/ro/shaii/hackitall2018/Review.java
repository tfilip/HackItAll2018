package ro.shaii.hackitall2018;

/**
 * Created by filip on 24-Mar-18.
 */

public class Review {

    private int stars;
    private String comm;
    private User client;

    public Review(){

    }


    public Review(int stars, String comm, User client) {
        this.stars = stars;
        this.comm = comm;
        this.client = client;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
}
