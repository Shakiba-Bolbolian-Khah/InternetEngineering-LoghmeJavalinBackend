package ie.CAs;
import java.util.*;

public class Loghme {
    private Restaurant[] restaurants;
    private User user;

    public Loghme(Restaurant[] restaurants, User user) {
        this.restaurants = restaurants;
        this.user = user;
    }

    public Restaurant[] getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurant[] restaurants) {
        this.restaurants = restaurants;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
