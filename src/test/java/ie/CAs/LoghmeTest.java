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
        String expected = "Recommended restaurant(s) based on your location:\n1. Perperook";
        assertEquals(commandHandler.getLoghme().getRecommendedRestaurants(),expected);
    }

    @Test
    public void testRecommended2() throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/testRecommended.txt")));
        String restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        String expected = "Recommended restaurant(s) based on your location:\n1. Perperook\n2. Hesturan";
        assertEquals(commandHandler.getLoghme().getRecommendedRestaurants(),expected);
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
        String expected = "Recommended restaurant(s) based on your location:\n1. Perperook\n2. Grill17\n3. Hesturan";
        assertEquals(commandHandler.getLoghme().getRecommendedRestaurants(),expected);
    }

    @Test
    public void testRecommendedAll() throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/testRecommended.txt")));
        while (true){
            String restaurantInfo = buff.readLine();
            if (restaurantInfo == null){
                break;
            }
            commandHandler.addRestaurant(restaurantInfo);
        }
        String expected = "Recommended restaurant(s) based on your location:\n1. Ali baba\n2. Perperook\n3. Grill17";
        assertEquals(commandHandler.getLoghme().getRecommendedRestaurants(),expected);
    }
}
