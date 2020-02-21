package ie.CAs;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CommandHandler {
    Loghme loghme;

    public CommandHandler(ArrayList<Restaurant> restaurants, ArrayList<Delivery> deliveries) {
        this.loghme = new Loghme(restaurants, deliveries);
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

    public void setFoodParty(String newPartyRestaurants){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Restaurant> partyRestaurants = gson.fromJson(newPartyRestaurants, new TypeToken<ArrayList<Restaurant>>(){}.getType());
        setPartyRestaurants(partyRestaurants);
        setPartyFoods(newPartyRestaurants);
    }

    public void setPartyRestaurants(ArrayList<Restaurant> partyRestaurants){
        for(Restaurant restaurant: partyRestaurants){
            if(!loghme.hasResraurant(restaurant.getId())){
                restaurant.clearMenu();
                try {
                    loghme.addRestaurant(restaurant);
                } catch (ErrorHandler errorHandler) {
                    System.err.print(errorHandler);
                }
            }
        }
    }

    public void setPartyFoods(String newPartyRestaurants){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray restaurantsArray = new JsonParser().parse(newPartyRestaurants).getAsJsonArray();
        ArrayList<PartyFood> partyFoods = new ArrayList<>();

        for (int i = 0; i < restaurantsArray.size(); i++) {
            JsonArray newMenu = restaurantsArray.get(i).getAsJsonObject().get("menu").getAsJsonArray();
            String newRestaurantId = restaurantsArray.get(i).getAsJsonObject().get("id").getAsString();
            String newRestaurantName = restaurantsArray.get(i).getAsJsonObject().get("name").getAsString();
            ArrayList<PartyFood> newPartyFoods = gson.fromJson(newMenu, new TypeToken<ArrayList<PartyFood>>(){}.getType());
            newPartyFoods.forEach((u) -> u.setRestaurantId(newRestaurantId));
            newPartyFoods.forEach((u) -> u.setRestaurantName(newRestaurantName));
            partyFoods.addAll(newPartyFoods);
        }

        loghme.setFoodParty(partyFoods);
    }

    public void showFoodParty(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String foodPartyInfo = null;
        foodPartyInfo = gson.toJson(loghme.getFoodParty());
        System.out.println(foodPartyInfo);
    }
}