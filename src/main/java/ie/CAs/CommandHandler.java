package ie.CAs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

public class CommandHandler {
    Loghme loghme;

    public CommandHandler(ArrayList<Restaurant> restaurants) {
        this.loghme = new Loghme(restaurants);
    }

    public Loghme getLoghme() {
        return loghme;
    }

    public void addRestaurant(String newRestaurantInfo){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Restaurant newRestaurant = null;
        try {
            newRestaurant = gson.fromJson(newRestaurantInfo, Restaurant.class);
            System.out.println(loghme.addRestaurant(newRestaurant));

        } catch (JsonSyntaxException e) {
            System.out.println("Error Wrong IO Command: Wrong JSON input.");
        } catch (ErrorHandler errorHandler){
            System.err.print(errorHandler);
        }
    }

    public void addFood(String newFoodInfo){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Food newFood = null;
        String restaurantName = "";
        try {
            newFood = gson.fromJson(newFoodInfo, Food.class);
            restaurantName = new JsonParser().parse(newFoodInfo).getAsJsonObject().get("restaurantName").getAsString();
        } catch (JsonSyntaxException e) {
            System.out.println("Error Wrong IO Command: Wrong JSON input.");
        }
        try {
            System.out.println(loghme.addFood(newFood, restaurantName));
        } catch (ErrorHandler errorHandler) {
            System.err.print(errorHandler);
        }
    }

    public void getRestaurants(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String restaurantsInfo = null;
        try {
            restaurantsInfo = gson.toJson(loghme.getRestaurants());
        } catch (ErrorHandler errorHandler) {
            System.err.print(errorHandler);
        }
        System.out.println(restaurantsInfo);
    }

    public void getRestaurant(String JsonRestaurantName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            String restaurantId = new JsonParser().parse(JsonRestaurantName).getAsJsonObject().get("name").getAsString();
            System.out.println(gson.toJson(loghme.getRestaurant(restaurantId)));
        } catch (JsonSyntaxException e) {
            System.out.println("Error Wrong IO Command: Wrong JSON input.");
        } catch (ErrorHandler errorHandler){
            System.err.print(errorHandler);
        }
    }

    public void getFood(String newFoodInfo){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String restaurantName = null, foodName = null;
        try {
            restaurantName = new JsonParser().parse(newFoodInfo).getAsJsonObject().get("restaurantName").getAsString();
            foodName = new JsonParser().parse(newFoodInfo).getAsJsonObject().get("foodName").getAsString();
            System.out.println(gson.toJson(loghme.getFood(restaurantName, foodName)));
        } catch (JsonSyntaxException e) {
            System.out.println("Error Wrong IO Command: Wrong JSON input.");
        } catch (ErrorHandler errorHandler){
            System.err.print(errorHandler);
        }
    }

    public void addToCart(String newFoodInfo){
        String restaurantName = "", foodName ="";
        try {
            restaurantName = new JsonParser().parse(newFoodInfo).getAsJsonObject().get("restaurantName").getAsString();
            foodName = new JsonParser().parse(newFoodInfo).getAsJsonObject().get("foodName").getAsString();
            System.out.println(loghme.addToCart(restaurantName, foodName));
        } catch (JsonSyntaxException e) {
            System.out.println("Error Wrong IO Command: Wrong JSON input.");
        } catch (ErrorHandler errorHandler){
            System.err.print(errorHandler);
        }
    }

    public void getCart(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try{
            System.out.println(gson.toJson(loghme.getCart()));
        } catch (ErrorHandler errorHandler){
            System.err.print(errorHandler);
        }
    }

    public void finalizeOrder(){
        String finalizationResult = "";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try{
            finalizationResult = gson.toJson(loghme.finalizeOrder());
            finalizationResult += "\nOrder finalization done successfully!";
            System.out.println(finalizationResult);
        } catch (ErrorHandler errorHandler){
            System.err.print(errorHandler);
        }
    }

    public void getRecommendedRestaurants(){
        try {
            System.out.println(loghme.getRecommendedRestaurants());
        } catch (ErrorHandler errorHandler){
            System.err.print(errorHandler);
        }
    }
}