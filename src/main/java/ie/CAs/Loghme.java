package ie.CAs;

import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Loghme {
    private ArrayList<Restaurant> restaurants;
    private User user;

    public Loghme(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
        this.user = new User("Ehsan","Khames Paneh","09123456789","ekhamespanah@yahoo.com",new Location(0,0),10000,new ShoppingCart(true));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String addRestaurant(Restaurant newRestaurant) throws ErrorHandler {
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getId().equals(newRestaurant.getId())){
                throw new ErrorHandler("Error: \"" + newRestaurant.getName() + "\" restaurant was added before!");
            }
        }
        restaurants.ensureCapacity(restaurants.size()+1);
        restaurants.add(newRestaurant);
        return "\"" + newRestaurant.getName() + "\" restaurant has been added successfully!";
    }

    public String addFood(Food newFood, String restaurantName) throws ErrorHandler {
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(restaurantName)){
                return restaurants.get(i).addFood(newFood);
            }
        }
        throw new ErrorHandler("Error: No \"" + restaurantName + "\" restaurant exists!");
    }

    public Double calculateDistance(Location restaurantLocation, Location userLocation) {
        int xDistance = restaurantLocation.getX() - userLocation.getX();
        int yDistance = restaurantLocation.getY() - userLocation.getY();
        return sqrt(pow(xDistance, 2) + pow(yDistance, 2));
    }

    public ArrayList<Restaurant> findNearestRestaurantsForUser() throws ErrorHandler {
        ArrayList<Restaurant> nearRestaurants = new ArrayList<Restaurant>();
        if(restaurants.size() == 0){
            throw new ErrorHandler("Error: Sorry there is no restaurant in Loghme at this time!");
        }
        for (int i = 0; i<restaurants.size(); i++) {
            Double distance = calculateDistance(restaurants.get(i).getLocation(), user.getLocation());
            if (distance <= 170){
                nearRestaurants.add(restaurants.get(i));
            }
        }
        return nearRestaurants;
    }

    public ArrayList<Restaurant> getRestaurants() throws ErrorHandler {
        if(restaurants.size()==0){
            throw new ErrorHandler("Error: Sorry there is no restaurant in Loghme at this time!");
        }
        return this.findNearestRestaurantsForUser();
    }

    public Restaurant getRestaurant(String restaurantId) throws ErrorHandler{
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getId().equals(restaurantId)){
                if (calculateDistance(restaurants.get(i).getLocation(), user.getLocation()) <= 170)
                    return restaurants.get(i);
                else
                    throw new ErrorHandler("403");
            }
        }
        throw new ErrorHandler("404");
    }

    public Food getFood(String restaurantName, String foodName) throws ErrorHandler {
        for (int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(restaurantName)){
                return restaurants.get(i).getFood(foodName);
            }
        }
        throw new ErrorHandler("Error: No \"" + restaurantName +"\" restaurant exists!");
    }

    public String addToCart(String restaurantName, String foodName) throws ErrorHandler{
        if(user.getShoppingCart().isEmpty()){
            user.setShoppingCartRestaurant(restaurantName);
        }
        else if(!(user.getShoppingCart().getRestaurantName().equals(restaurantName))){
            throw new ErrorHandler("Error: You chose \""+user.getShoppingCart().getRestaurantName()+"\" before! Choosing two restaurants is invalid!");
        }

        for(int i = 0;i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(restaurantName)){
                Food orderedFood = restaurants.get(i).getOrderedFood(foodName);
                if(orderedFood != null) {
                    return user.addToCart(orderedFood);
                }
                else{
                    throw new ErrorHandler("Error: There is no \"" + foodName + "\" in \""+restaurantName+"\" restaurant menu!");
                }
            }
        }
        throw new ErrorHandler("Error: No \"" + restaurantName +"\" restaurant exists!\n");
    }

    public Map<String, Integer> getCart() throws ErrorHandler {
        return user.getCart();
    }

    public Map<String, Integer> finalizeOrder() throws ErrorHandler {
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

    public String getRecommendedRestaurants() throws ErrorHandler {
        Map<String,Double> scores = new HashMap<>();
        if(restaurants.size() == 0){
            throw new ErrorHandler("Error: Sorry there is no restaurant in Loghme at this time!");
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
            if(i < 4)
                recommended += i + ". " + entry.getKey() + "\n";
            i++;
        }
        return recommended.substring(0, recommended.length()-1);
    }
}