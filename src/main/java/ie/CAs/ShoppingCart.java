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

    public String addToCart(Food newFood){
        boolean isAdded = false;
        for( int i = 0; i < orderedFoods.size(); i++){
            if(orderedFoods.get(i).getFood().equals(newFood)){
                orderedFoods.get(i).IncreaseNumber();
                isAdded = true;
            }
        }
        if(!isAdded){
            orderedFoods.add(new Order(newFood,1));
        }
        return "\""+newFood.getName()+"\" has been added to your cart successfully!";
    }

    public String getCart(){
        if(orderedFoods.isEmpty()){
            return "Error: There is nothing to be finalized in your cart!";
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String,Integer> foods = new HashMap<>();
        for (int i = 0; i<orderedFoods.size(); i++){
            foods.put(orderedFoods.get(i).getFood().getName(),orderedFoods.get(i).getNumber());
        }
        return gson.toJson(foods);
    }

    public String finalizeOrder(){
        String finalizationResult = getCart();
        if(finalizationResult.equals("Error: There is nothing to be finalized in your cart!")) {
            return finalizationResult;
        }
        finalizationResult += "\nOrder finalization done successfully!";
        orderedFoods.clear();
        return finalizationResult;
    }
}
