package ie.CAs;
import java.util.*;

public class ShoppingCart {
    private boolean isEmpty;
    private String restaurantName;
    private Map<Food, Integer> orderedFoods;

    public ShoppingCart(boolean isEmpty) {
        this.isEmpty = isEmpty;
        orderedFoods = new HashMap<>();
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
        this.isEmpty = false;
    }

    public Map<Food, Integer> getOrderedFoods() {
        return orderedFoods;
    }

    public void setOrderedFoods(Map<Food, Integer> orderedFoods) {
        this.orderedFoods = orderedFoods;
    }

    public String addToCart(Food newFood){
        if(orderedFoods.containsKey(newFood)){
            orderedFoods.put(newFood, orderedFoods.get(newFood) +1);
        }
        else{
            orderedFoods.put(newFood,1);
        }
        return "\""+newFood.getName()+"\" has been added to your cart successfully!\n";
    }

    public String getCart(){
        return "Don't know!!"; //ToDo: handle it!
    }
}
