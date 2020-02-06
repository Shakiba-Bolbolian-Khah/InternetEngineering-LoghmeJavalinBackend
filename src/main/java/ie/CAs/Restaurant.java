package ie.CAs;
import java.util.*;

public class Restaurant{
    private String name;
    private String description;
    private Location location;
    private Food[] menu;

    public Restaurant(String name, String description, Location location, Food[] menu) {
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

    public Food[] getMenu() {
        return menu;
    }

    public void setMenu(Food[] menu) {
        this.menu = menu;
    }
}
