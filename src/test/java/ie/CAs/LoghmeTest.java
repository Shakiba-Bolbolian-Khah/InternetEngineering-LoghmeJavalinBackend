package ie.CAs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoghmeTest {
    CommandHandler commandHandler;
    @Before
    public void setup(){
        commandHandler = new CommandHandler();
    }

    @Test
    public void testAddRestaurant(){
        String restaurantInfo = "{\"name\": \"Hesturan\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it’s yummy!\", \"popularity\": 0.8, \"price\":20000}, {\"name\": \"Kabab\", \"description\": \"it’s delicious!\", \"popularity\": 0.6, \"price\":30000}]}";
        commandHandler.addRestaurant(restaurantInfo);
//        assertEquals(commandHandler.getLoghme().getRestaurant("Hesturan"),"{\"name\": \"Hesturan\", \"description\": \"luxury\", \"location\": {\"x\": 1, \"y\": 3}, \"menu\": [{\"name\": \"Gheime\", \"description\": \"it’s yummy!\", \"popularity\": 0.8, \"price\":20000}, {\"name\": \"Kabab\", \"description\": \"it’s delicious!\", \"popularity\": 0.6, \"price\":30000}]}");

    }
}
