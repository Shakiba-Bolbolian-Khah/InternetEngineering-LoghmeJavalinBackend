package ie.CAs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Restaurant{
    private String name;
    private String description;
    private Location location;
    private ArrayList<Food> menu;

    public Restaurant(String name, String description, Location location, ArrayList<Food> menu) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Food> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Food> menu) {
        this.menu = menu;
    }

    public String getFood(String foodName){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for (int i = 0; i < menu.size(); i++){
            if(menu.get(i).getName().equals(foodName)){
                return gson.toJson(menu.get(i));
            }
        }
        return "Error: No \"" + foodName +"\" in \"" + this.name + "\" restaurant exists!";
    }

    public String addFood(Food newFood){
        for(int i = 0; i < menu.size(); i++){
            if(menu.get(i).getName().equals(newFood.getName())){
                return "Error: \""+newFood.getName() + "\" had been added in \""+name+"\" menu before!";
            }
        }
        menu.ensureCapacity(menu.size()+1);
        menu.add(newFood);
        return "\""+newFood.getName()+"\" food has been added in \""+name+"\" menu successfully!";
    }

    public Food getOrderedFood(String foodName){
        for( int i = 0; i < menu.size(); i++){
            if(menu.get(i).getName().equals(foodName)){
                return menu.get(i);
            }
        }
        return null;
    }
}
