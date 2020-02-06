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

    public String getFood(String foodName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < menu.size(); i++){
            if(menu.get(i).getName().equals(foodName)){
                return objectMapper.writeValueAsString(menu.get(i));
            }
        }
        return "Error 404 Not Found: No \"" + foodName +"\" in \"" + this.name + "\" restaurant exists!\n";
    }
}
