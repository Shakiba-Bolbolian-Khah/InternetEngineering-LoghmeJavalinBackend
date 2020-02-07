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
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(newRestaurant.getName())){
                return "Error: \"" + newRestaurant.getName() + "\" restaurant was added before!\n";
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
        return "Error: No \"" + restaurantName + "\" restaurant exists!\n";
    }

    public String getRestaurants(){
        String restaurantsInfo = "";
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < restaurants.size(); i++){
            String restaurantJsonForm = null;
            try {
                restaurantJsonForm = objectMapper.writeValueAsString(restaurants.get(i));
            } catch (JsonProcessingException e) {
                return "Error: Converting data to JSON format to show available restaurants faced a problem!\n";
            }
            restaurantsInfo = restaurantsInfo + restaurantJsonForm;
        }
        return restaurantsInfo;
    }

    public String getRestaurant(String restaurantName){
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(restaurantName)){
                try {
                    return objectMapper.writeValueAsString(restaurants.get(i));
                } catch (JsonProcessingException e) {
                    return "Error: Converting data to JSON format to show \""+restaurantName+"\" restaurant data faced a problem!\n";
                }
            }
        }
        return "Error: No \"" + restaurantName +"\" restaurant exists!\n";
    }

    public String getFood(String restaurantName, String foodName){
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(restaurantName)){
                return restaurants.get(i).getFood(foodName);
            }
        }
        return "Error: No \"" + restaurantName +"\" restaurant exists!\n";
    }

    public String addToCart(String restaurantName, String foodName){
        if(user.getShoppingCart().isEmpty()){
            user.setShoppingCartRestaurant(restaurantName);
        }
        else if(!(user.getShoppingCart().getRestaurantName().equals(restaurantName))){
            return "Error: You chose \""+user.getShoppingCart().getRestaurantName()+"\" before! Choosing two restaurants is invalid!\n";
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
        return user.getCart();//ToDo: Should be fixed!
    }

    public String finalizeOrder(){
        return "Order finalized successfully!\n";
    }

    public String getRecommendedRestaurants(){
        return "Here you are!";
    }
}
