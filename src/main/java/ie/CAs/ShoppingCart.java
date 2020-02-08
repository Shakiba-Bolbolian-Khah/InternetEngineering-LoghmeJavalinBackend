package ie.CAs;

import com.google.gson.Gson;

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
        return "\""+newFood.getName()+"\" has been added to your cart successfully!";
    }

    public String getCart(){
        Gson gson = new Gson();
        Map<String,Integer> foods = new HashMap<>();
        for (Map.Entry<Food,Integer> entry : orderedFoods.entrySet()){
            foods.put(entry.getKey().getName(),entry.getValue());
        }
        return gson.toJson(foods);
    }

    public String finalizeOrder(){
        String finalizationResult = getCart();
        finalizationResult += "\nOrder finalization done successfully!";
        orderedFoods.clear();
        return finalizationResult;
    }
}
