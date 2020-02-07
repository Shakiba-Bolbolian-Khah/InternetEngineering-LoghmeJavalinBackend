package ie.CAs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.*;

public class Loghme {
    private ArrayList<Restaurant> restaurants;
    private User user;

    public Loghme() {
        this.restaurants = new ArrayList<Restaurant>();
        this.user = new User(new Location(0,0),new ShoppingCart(true));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String addRestaurant(Restaurant newRestaurant){
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(newRestaurant.getName())){
                return "Error: \"" + newRestaurant.getName() + "\" restaurant was added before!";
            }
        }
        restaurants.ensureCapacity(restaurants.size()+1);
        restaurants.add(newRestaurant);
        return "\"" + newRestaurant.getName() + "\" restaurant has been added successfully!";
    }

    public String addFood(Food newFood, String restaurantName){
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(restaurantName)){
                return restaurants.get(i).addFood(newFood);
            }
        }
        return "Error: No \"" + restaurantName + "\" restaurant exists!";
    }

    public String getRestaurants(){
        String restaurantsInfo = "";
        Gson gson = new Gson();
        restaurantsInfo = gson.toJson(restaurants);
        return restaurantsInfo;
    }

    public String getRestaurant(String restaurantName){
        Gson gson = new Gson();
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(restaurantName)){
                return gson.toJson(restaurants.get(i));
            }
        }
        return "Error: No \"" + restaurantName +"\" restaurant exists!";
    }

    public String getFood(String restaurantName, String foodName){
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(restaurantName)){
                return restaurants.get(i).getFood(foodName);
            }
        }
        return "Error: No \"" + restaurantName +"\" restaurant exists!";
    }

    public String addToCart(String restaurantName, String foodName){
        if(user.getShoppingCart().isEmpty()){
            user.setShoppingCartRestaurant(restaurantName);
        }
        else if(!(user.getShoppingCart().getRestaurantName().equals(restaurantName))){
            return "Error: You chose \""+user.getShoppingCart().getRestaurantName()+"\" before! Choosing two restaurants is invalid!";
        }

        for(int i = 0;i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(restaurantName)){
                Food orderedFood = restaurants.get(i).getOrderedFood(foodName);
                if(orderedFood != null) {
                    return user.addToCart(orderedFood);
                }
                else{
                    return "Error: There is no \"" + foodName + "\" in \""+restaurantName+"\" restaurant menu!";
                }
            }
        }
        return "Error: No \"" + restaurantName +"\" restaurant exists!\n";
    }

    public String getCart(){
        return user.getCart();
    }

    public String finalizeOrder(){
        return user.finalizeOrder();
    }

    public String getRecommendedRestaurants(){
        return "Here you are!";
    }
}
