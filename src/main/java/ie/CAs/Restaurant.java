package ie.CAs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < menu.size(); i++){
            if(menu.get(i).getName().equals(foodName)){
                try {
                    return objectMapper.writeValueAsString(menu.get(i));
                } catch (JsonProcessingException e) {
                    return "Error: Converting data to JSON format to show \""+foodName+"\" info in \""+name+"\" restaurant faced a problem!\n";
                }
            }
        }
        return "Error: No \"" + foodName +"\" in \"" + this.name + "\" restaurant exists!\n";
    }

    public String addFood(Food newFood){
        for(int i = 0; i < menu.size(); i++){
            if(menu.get(i).getName().equals(newFood.getName())){
                return "Error: \""+newFood.getName() + "\" had been added in \""+name+"\" menu before!\n";
            }
        }
        menu.ensureCapacity(menu.size()+1);
        menu.add(newFood);
        return "\""+newFood.getName()+"\" food has been added in \""+name+"\" menu successfully!\n";
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
