package ie.CAs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        System.out.println(loghme.getRestaurants());
    }

    public void getRestaurant(String restaurantName){
        System.out.println(loghme.getRestaurant(restaurantName));
    }

    public void getFood(String newFoodInfo){
        String restaurantName = new JsonParser().parse(newFoodInfo).getAsJsonObject().get("restaurantName").getAsString();
        String foodName = new JsonParser().parse(newFoodInfo).getAsJsonObject().get("foodName").getAsString();
        System.out.println(loghme.getFood(restaurantName, foodName));
    }

    public void addToCart(String newFoodInfo){
        String restaurantName = new JsonParser().parse(newFoodInfo).getAsJsonObject().get("restaurantName").getAsString();
        String foodName = new JsonParser().parse(newFoodInfo).getAsJsonObject().get("foodName").getAsString();
        System.out.println(loghme.addToCart(restaurantName, foodName));
    }

    public void getCart(){
        System.out.println(loghme.getCart());
    }

    public void finalizeOrder(){
        System.out.println(loghme.finalizeOrder());
    }

    public void getRecommendedRestaurants(){
        System.out.println(loghme.getRecommendedRestaurants());
    }
}