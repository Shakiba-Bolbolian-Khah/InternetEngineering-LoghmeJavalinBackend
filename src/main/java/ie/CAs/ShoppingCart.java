package ie.CAs;

import java.util.*;

public class ShoppingCart {
    private boolean isEmpty;
    private String restaurantId;
    private String restaurantName;
    private int totalPayment;
    private boolean isFoodParty;
    private ArrayList<ShoppingCartItem> items;

    public ShoppingCart(boolean isEmpty) {
        this.isEmpty = isEmpty;
        this.items = new ArrayList<>();
        this.totalPayment = 0;
        this.isFoodParty = false;
    }

    public boolean isFoodParty() {
        return isFoodParty;
    }

    public void setIsFoodParty(boolean foodParty) {
        isFoodParty = foodParty;
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

    public ArrayList<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShoppingCartItem> items) {
        this.items = items;
    }

    public int contain(Food food){
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getFood().equals(food)){
                return i;
            }
        }
        return -1;
    }

    public String addToCart(Food newFood){
        int foodIndex = contain(newFood);
        if(foodIndex == -1){
            items.add(new ShoppingCartItem(newFood,1));
        }
        else{
            items.get(foodIndex).IncreaseNumber();
        }
        calculateTotalPayment(newFood.getPrice());
        return "\""+newFood.getName()+"\" has been added to your cart successfully!";
    }

    public Map<String, Integer> getCart() throws ErrorHandler{
        if(items.isEmpty()){
            throw new ErrorHandler("Error: There is nothing to show in your cart!");
        }
        Map<String,Integer> foods = new HashMap<>();
        for (ShoppingCartItem item : items) {
            foods.put(item.getFood().getName(), item.getNumber());
        }
        return foods;
    }

    public void calculateTotalPayment(int newPrice) {
        totalPayment += newPrice;
    }

    public Map<String, Integer> finalizeOrder(int userCredit, boolean isFoodPartyFinished) throws ErrorHandler {
        Map<String, Integer> finalizationResult;
        try {
            finalizationResult = getCart();
        } catch (ErrorHandler e) {
            throw new ErrorHandler("400-1");
        }
        if(isFoodParty && isFoodPartyFinished){
            clearCart();
            throw new ErrorHandler("403");
        }
        if (userCredit >= totalPayment){
            clearCart();
            return finalizationResult;
        }
        else
            throw new ErrorHandler("400-2");
    }

    public void clearCart(){
        items.clear();
        totalPayment = 0;
        isEmpty = true;
        isFoodParty = false;
    }
}
