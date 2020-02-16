package ie.CAs;

import java.util.*;

public class ShoppingCart {
    private boolean isEmpty;
    private String restaurantId;
    private String restaurantName;
    private int totalPayment;
    private ArrayList<Order> orderedFoods;

    public ShoppingCart(boolean isEmpty) {
        this.isEmpty = isEmpty;
        this.orderedFoods = new ArrayList<>();
        this.totalPayment = 0;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getTotalPayment() {
        return totalPayment;
    }

    public void setRestaurantName(String restaurantId, String restaurantName) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.isEmpty = false;
    }

    public ArrayList<Order> getOrderedFoods() {
        return orderedFoods;
    }

    public void setOrderedFoods(ArrayList<Order> orderedFoods) {
        this.orderedFoods = orderedFoods;
    }

    public int contain(Food food){
        for(int i = 0; i < orderedFoods.size(); i++){
            if(orderedFoods.get(i).getFood().equals(food)){
                return i;
            }
        }
        return -1;
    }

    public String addToCart(Food newFood){
        int foodIndex = contain(newFood);
        if(foodIndex == -1){
            orderedFoods.add(new Order(newFood,1));
        }
        else{
            orderedFoods.get(foodIndex).IncreaseNumber();
        }
        calculateTotalPayment(newFood.getPrice());
        return "\""+newFood.getName()+"\" has been added to your cart successfully!";
    }

    public Map<String, Integer> getCart() throws ErrorHandler{
        if(orderedFoods.isEmpty()){
            throw new ErrorHandler("Error: There is nothing to show in your cart!");
        }
        Map<String,Integer> foods = new HashMap<>();
        for (Order orderedFood : orderedFoods) {
            foods.put(orderedFood.getFood().getName(), orderedFood.getNumber());
        }
        return foods;
    }

    public void calculateTotalPayment(int newPrice) {
        totalPayment += newPrice;
    }

    public Map<String, Integer> finalizeOrder(int userCredit) throws ErrorHandler {
        Map<String, Integer> finalizationResult;
        try {
            finalizationResult = getCart();
        } catch (ErrorHandler e) {
            throw new ErrorHandler("400-1");
        }
        if (userCredit >= totalPayment){
            orderedFoods.clear();
            totalPayment = 0;
            return finalizationResult;
        }
        else
            throw new ErrorHandler("400-2");
    }
}
