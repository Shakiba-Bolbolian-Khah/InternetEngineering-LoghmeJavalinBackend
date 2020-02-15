package ie.CAs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Restaurant{
    private String name;
    private String id;
    private String description;
    private Location location;
    private ArrayList<Food> menu;
    private String logo;

    public Restaurant(String name, String id, String description, Location location, ArrayList<Food> menu, String logo) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.location = location;
        this.menu = menu;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLogoUrl() {
        return logo;
    }

    public void setLogoUrl(String logoUrl) {
        this.logo = logoUrl;
    }

    public Food getFood(String foodName) throws ErrorHandler {
        for (int i = 0; i < menu.size(); i++){
            if(menu.get(i).getName().equals(foodName)){
                return menu.get(i);
            }
        }
        throw new ErrorHandler("Error: No \"" + foodName +"\" in \"" + this.name + "\" restaurant exists!");
    }

    public String addFood(Food newFood) throws ErrorHandler{
        for(int i = 0; i < menu.size(); i++){
            if(menu.get(i).getName().equals(newFood.getName())){
                throw new ErrorHandler("Error: \""+newFood.getName() + "\" had been added in \""+name+"\" menu before!");
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

    public Double getScore(){
        Double score = 0.0;
        for (int i = 0; i < menu.size(); i++){
            score += menu.get(i).getPopularity();
        }
        if (score != 0.0) {
            score = score / menu.size();
        }
        return score;
    }
}
