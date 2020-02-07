package ie.CAs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CommandHandler {
    Loghme loghme;

    public CommandHandler() {
        this.loghme = new Loghme();
    }

    public void addRestaurant(String newRestaurantInfo){
        Gson gson = new GsonBuilder().create();
        Restaurant newRestaurant = gson.fromJson(newRestaurantInfo, Restaurant.class);
        System.out.println(loghme.addRestaurant(newRestaurant));
    }

    public void addFood(String newFoodInfo){
        Gson gson = new GsonBuilder().create();
        Food newFood = gson.fromJson(newFoodInfo, Food.class);
        String restaurantName = new JsonParser().parse(newFoodInfo).getAsJsonObject().get("restaurantName").getAsString();
        System.out.println(loghme.addFood(newFood, restaurantName));
    }

    public void getRestaurants(){

    }

    public void getRestaurant(String restaurantName){

    }

    public void getFood(String foodInfo){

    }

    public void addToCart(String foodInfo){

    }

    public void getCart(){

    }

    public void finalizeOrder(){

    }

    public void getRecommendedRestaurants(){

    }
}