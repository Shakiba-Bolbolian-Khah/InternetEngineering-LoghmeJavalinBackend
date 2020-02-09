package ie.CAs;

import org.junit.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoghmeTest {
    CommandHandler commandHandler;

    public String removeWhiteSpaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    public void setRestaurantsForFinalizingOrderTest() throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/testRecommended.txt")));
        while (true){
            String restaurantInfo = buff.readLine();
            if (restaurantInfo == null){
                break;
            }
            commandHandler.addRestaurant(restaurantInfo);
        }
    }

    public void setFoodsForFinalizingOrderTest(int num) throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/testFinalizeOrder" + num + ".txt")));
        while (true){
            String foodInfo = buff.readLine();
            if (foodInfo == null){
                break;
            }
            commandHandler.addToCart(foodInfo);
        }
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
        setRestaurantsForFinalizingOrderTest();
        String expected = "Recommended restaurant(s) based on your location:\n1. Ali baba\n2. Perperook\n3. Grill17";
        assertEquals(commandHandler.getLoghme().getRecommendedRestaurants(),expected);
    }

    @Test
    public void testFinalize1() throws IOException {
        setRestaurantsForFinalizingOrderTest();
        String expected = "Error: There is nothing to be finalized in your cart!";
        assertEquals(commandHandler.getLoghme().finalizeOrder(),expected);
    }

    @Test
    public void testFinalize2() throws IOException {
        setRestaurantsForFinalizingOrderTest();
        setFoodsForFinalizingOrderTest(2);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/testFinalizeOut2.txt")));
        assertEquals(removeWhiteSpaces(commandHandler.getLoghme().finalizeOrder()),removeWhiteSpaces(expected));
    }

    @Test
    public void testFinalize3() throws IOException {
        setRestaurantsForFinalizingOrderTest();
        setFoodsForFinalizingOrderTest(3);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/testFinalizeOut3.txt")));
        assertEquals(removeWhiteSpaces(commandHandler.getLoghme().finalizeOrder()),removeWhiteSpaces(expected));
    }

    @Test
    public void testFinalize4() throws IOException {
        setRestaurantsForFinalizingOrderTest();
        setFoodsForFinalizingOrderTest(4);
        String output = removeWhiteSpaces(commandHandler.getLoghme().finalizeOrder());
        assertTrue(output.contains("\"GhormeSabzi\":1"));
        assertTrue(output.contains("\"Kebab\":1"));
        assertTrue(output.contains("\"Gheime\":1"));
    }

    @Test
    public void testFinalizeAll() throws IOException {
        setRestaurantsForFinalizingOrderTest();
        setFoodsForFinalizingOrderTest(5);
        String output = removeWhiteSpaces(commandHandler.getLoghme().finalizeOrder());
        assertTrue(output.contains("\"Fesenjoon\":2"));
        assertTrue(output.contains("\"TahChin\":4"));
        assertTrue(output.contains("\"ZereshkPolo\":3"));
    }
    @After
    public void finish(){
        commandHandler = null;
    }
}