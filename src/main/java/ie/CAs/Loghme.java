package ie.CAs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        return "Restaurant added successfully!";
    }

    public String addFood(Food newFood, String restaurantName){
        return "Food added successfully!";
    }

    public String getRestaurants() throws JsonProcessingException{
        String restaurantsInfo = "";
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < restaurants.size(); i++){
            String restaurantJsonForm = objectMapper.writeValueAsString(restaurants.get(i));
            restaurantsInfo = restaurantsInfo + restaurantJsonForm;
        }
        return restaurantsInfo;
    }

    public String getRestaurant(String restaurantName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(restaurantName)){
                return objectMapper.writeValueAsString(restaurants.get(i));
            }
        }
        return "Error 404 Not Found: No \"" + restaurantName +"\" restaurant exists!\n";
    }

    public String getFood(String restaurantName, String foodName) throws JsonProcessingException {
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(restaurantName)){
                return restaurants.get(i).getFood(foodName);
            }
        }
        return "Error 404 Not Found: No \"" + restaurantName +"\" restaurant exists!\n";
    }

    public String addToCart(String restaurantName, String foodName){
        return "Food added to cart successfully!";
    }

    public String getCart(){
        String cart = "";
        return cart;
    }

    public String finalizeOrder(){
        return "Order finalized successfully!\n";
    }

    public String getRecommendedRestaurants(){
        return "Here you are!";
    }
}
