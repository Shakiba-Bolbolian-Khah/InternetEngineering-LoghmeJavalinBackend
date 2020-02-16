package ie.CAs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class ShoppingCart {
    private boolean isEmpty;
    private String restaurantName;
    private ArrayList<Order> orderedFoods;

    public ShoppingCart(boolean isEmpty) {
        this.isEmpty = isEmpty;
        orderedFoods = new ArrayList<Order>();
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

    public ArrayList<Order> getOrderedFoods() {
        return orderedFoods;
    }

    public void setOrderedFoods(ArrayList<Order> orderedFoods) {
        this.orderedFoods = orderedFoods;
    }

    public int contain(Food food){
        for(int i = 0; i < orderedFoods.size(); i++){
            if(orderedFoods.get(i).getFood().equals(food)){
                return i;
            }
        }
        return -1;
    }

    public String addToCart(Food newFood){
        int foodIndex = contain(newFood);
        if(foodIndex == -1){
            orderedFoods.add(new Order(newFood,1));
        }
        else{
            orderedFoods.get(foodIndex).IncreaseNumber();
        }
        return "\""+newFood.getName()+"\" has been added to your cart successfully!";
    }

    public Map<String, Integer> getCart() throws ErrorHandler{
        if(orderedFoods.isEmpty()){
            throw new ErrorHandler("Error: There is nothing to show in your cart!");
        }
        Map<String,Integer> foods = new HashMap<>();
        for (int i = 0; i<orderedFoods.size(); i++){
            foods.put(orderedFoods.get(i).getFood().getName(),orderedFoods.get(i).getNumber());
        }
        return foods;
    }

    public Map<String, Integer> finalizeOrder() throws ErrorHandler {
        Map<String, Integer> finalizationResult = null;
        try {
            finalizationResult = getCart();
        } catch (ErrorHandler e) {
            throw new ErrorHandler("There is nothing to be finalized in your cart!");
        }
        orderedFoods.clear();
        return finalizationResult;
    }
}
