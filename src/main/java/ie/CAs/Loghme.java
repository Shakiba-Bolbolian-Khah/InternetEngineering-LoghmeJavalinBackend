package ie.CAs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Loghme {
    private ArrayList<Restaurant> restaurants;
    private User user;

    public Loghme() {
        this.restaurants = new ArrayList<Restaurant>();
        this.user = new User("Ehsan","Khames Paneh","09123456789","ekhamespanah@yahoo.com",new Location(0,0),10000,new ShoppingCart(true));
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        restaurantsInfo = gson.toJson(restaurants);
        return restaurantsInfo;
    }

    public String getRestaurant(String restaurantName){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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

    public static Map<String, Double> sortByValue(Map<String, Double> hm)
    {
        List<Map.Entry<String, Double> > list = new LinkedList<>(hm.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public String getRecommendedRestaurants(){
        Map<String,Double> scores = new HashMap<>();
        if(restaurants.size() == 0){
            return "Error: Sorry there is no restaurant in Loghme at this time!";
        }
        for (int i = 0; i<restaurants.size(); i++) {
            int xDistance = restaurants.get(i).getLocation().getX() - user.getLocation().getX();
            int yDistance = restaurants.get(i).getLocation().getY() - user.getLocation().getY();
            Double distance = sqrt(pow(xDistance, 2) + pow(yDistance, 2));
            scores.put(restaurants.get(i).getName(), restaurants.get(i).getScore() / distance);
        }
        String recommended = "Recommended restaurant(s) based on your location:\n";
        scores = sortByValue(scores);
        int i = 1;
        for (Map.Entry<String,Double> entry : scores.entrySet()){
            System.out.println(entry.getValue()+entry.getKey());
            if(i < 4)
                recommended += i + ". " + entry.getKey() + "\n";
            i++;
        }
        return recommended.substring(0, recommended.length()-1);
    }
}