package ie.CAs;

import org.junit.*;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class LoghmeTest {
    CommandHandler commandHandler;

    public String removeWhiteSpaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    @Before
    public void setup(){
        commandHandler = new CommandHandler();
    }

    @Test
    public void testRecommended0(){
        String expected = "Error: Sorry there is no restaurant in Loghme at this time!";
        assertEquals(removeWhiteSpaces(commandHandler.getLoghme().getRecommendedRestaurants()),removeWhiteSpaces(expected));
    }

    @Test
    public void testRecommended1() throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/testRecommended.txt")));
        String restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        String expected = "Recommended restaurant(s) based on your location: 1.Perperook";
        assertEquals(removeWhiteSpaces(commandHandler.getLoghme().getRecommendedRestaurants()),removeWhiteSpaces(expected));
    }

    @Test
    public void testRecommended2() throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/testRecommended.txt")));
        String restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        assertEquals(removeWhiteSpaces(commandHandler.getLoghme().getRecommendedRestaurants()),removeWhiteSpaces(restaurantInfo));
    }

    @Test
    public void testRecommended3() throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/testRecommended.txt")));
        String restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        assertEquals(removeWhiteSpaces(commandHandler.getLoghme().getRecommendedRestaurants()),removeWhiteSpaces(restaurantInfo));
    }

    @Test
    public void testRecommendedAll() throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/testRecommended.txt")));
        String restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        assertEquals(removeWhiteSpaces(commandHandler.getLoghme().getRecommendedRestaurants()),removeWhiteSpaces(restaurantInfo));
    }
}
