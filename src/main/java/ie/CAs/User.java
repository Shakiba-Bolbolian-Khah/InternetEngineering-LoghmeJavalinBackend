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

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
