package ie.CAs;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class LoghmeTest {
    CommandHandler commandHandler;
    @Before
    public void setup(){
        commandHandler = new CommandHandler();
    }

    @Test
    public void testAddRestaurant() throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/testAddRestaurant.json")));
        String restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        assertEquals(commandHandler.getLoghme().getRestaurant("Hesturan"),restaurantInfo);
    }
}
