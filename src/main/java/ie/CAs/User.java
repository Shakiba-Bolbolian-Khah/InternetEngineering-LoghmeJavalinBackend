package ie.CAs;
import java.util.*;

public class User {
    private Location location;
    private ShoppingCart shoppingCart;

    public User(Location location, ShoppingCart shoppingCart) {
        this.location = location;
        this.shoppingCart = shoppingCart;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCartRestaurant(String restaurantName){
        shoppingCart.setRestaurantName(restaurantName);
    }

    public String addToCart(Food newFood){
        return shoppingCart.addToCart(newFood);
    }

    public String getCart(){
        return shoppingCart.getCart(); //ToDo:Fix it
    }
}
