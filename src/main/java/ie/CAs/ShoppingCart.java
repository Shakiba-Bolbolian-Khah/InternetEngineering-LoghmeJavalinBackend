package ie.CAs;
import java.util.*;

public class ShoppingCart {
    private boolean isEmpty;
    private String restaurantName;
    private Map<Food, Integer> OrderedFoods;

    public ShoppingCart(boolean isEmpty, String restaurantName, Map<Food, Integer> orderedFoods) {
        this.isEmpty = isEmpty;
        this.restaurantName = restaurantName;
        OrderedFoods = orderedFoods;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Map<Food, Integer> getOrderedFoods() {
        return OrderedFoods;
    }

    public void setOrderedFoods(Map<Food, Integer> orderedFoods) {
        OrderedFoods = orderedFoods;
    }
}
