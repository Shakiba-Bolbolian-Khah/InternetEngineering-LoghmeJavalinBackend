package ie.CAs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class CommandHandler {
    Loghme loghme;

    public CommandHandler() {
        this.loghme = new Loghme();
    }

    public void addRestaurant(String newRestaurantInfo){
        Gson gson = new GsonBuilder().create();
        try {
            Restaurant newRestaurant = gson.fromJson(newRestaurantInfo, Restaurant.class);
            System.out.println(loghme.addRestaurant(newRestaurant));
        } catch (JsonSyntaxException e) {
            System.out.println("Error Wrong IO Command: Wrong JSON input.");
        }
    }

    public void addFood(String newFoodInfo){
        Gson gson = new GsonBuilder().create();
        try {
            Food newFood = gson.fromJson(newFoodInfo, Food.class);
            String restaurantName = new JsonParser().parse(newFoodInfo).getAsJsonObject().get("restaurantName").getAsString();
            System.out.println(loghme.addFood(newFood, restaurantName));
        } catch (JsonSyntaxException e) {
            System.out.println("Error Wrong IO Command: Wrong JSON input.");
        }
    }

    public void getRestaurants(){
        try {
            System.out.println(loghme.getRestaurants());
        } catch (JsonSyntaxException e) {
            System.out.println("Error Wrong IO Command: Wrong JSON input.");
        }
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