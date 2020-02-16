package ie.CAs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        commandHandler = new CommandHandler(restaurants);
    }

    @Test
    public void testRecommended0(){
        String expected = "Error: Sorry there is no restaurant in Loghme at this time!";
        try {
            assertEquals(removeWhiteSpaces(commandHandler.getLoghme().getRecommendedRestaurants()),removeWhiteSpaces(expected));
        } catch (ErrorHandler errorHandler){
            System.err.print(errorHandler);
        }
    }

    @Test
    public void testRecommended1() throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/testRecommended.txt")));
        String restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        String expected = "Recommended restaurant(s) based on your location:\n1. Perperook";
        try {
            assertEquals(commandHandler.getLoghme().getRecommendedRestaurants(),expected);
        } catch (ErrorHandler errorHandler){
            System.err.print(errorHandler);
        }

    }

    @Test
    public void testRecommended2() throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/testRecommended.txt")));
        String restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        restaurantInfo = buff.readLine();
        commandHandler.addRestaurant(restaurantInfo);
        String expected = "Recommended restaurant(s) based on your location:\n1. Perperook\n2. Hesturan";
        try {
            assertEquals(commandHandler.getLoghme().getRecommendedRestaurants(),expected);
        } catch (ErrorHandler errorHandler){
            System.out.print(errorHandler);
        }
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
        try {
            assertEquals(commandHandler.getLoghme().getRecommendedRestaurants(),expected);
        } catch (ErrorHandler errorHandler){
            System.err.print(errorHandler);
        }
    }

    @Test
    public void testRecommendedAll() throws IOException {
        setRestaurantsForFinalizingOrderTest();
        String expected = "Recommended restaurant(s) based on your location:\n1. Ali baba\n2. Perperook\n3. Grill17";
        try {
            assertEquals(commandHandler.getLoghme().getRecommendedRestaurants(),expected);
        } catch (ErrorHandler errorHandler){
            System.err.print(errorHandler);
        }
    }

    @Test (expected = ErrorHandler.class)
    public void testFinalize1() throws IOException, ErrorHandler {
        setRestaurantsForFinalizingOrderTest();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String expected = "There is nothing to be finilized in your cart!";
        assertEquals(gson.toJson(commandHandler.getLoghme().finalizeOrder()),expected);
    }

    @Test
    public void testFinalize2() throws IOException, ErrorHandler {
        setRestaurantsForFinalizingOrderTest();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        setFoodsForFinalizingOrderTest(2);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/testFinalizeOut2.txt")));
        assertEquals(removeWhiteSpaces(gson.toJson(commandHandler.getLoghme().finalizeOrder())),removeWhiteSpaces(expected));
    }

    @Test
    public void testFinalize3() throws IOException, ErrorHandler {
        setRestaurantsForFinalizingOrderTest();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        setFoodsForFinalizingOrderTest(3);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/testFinalizeOut3.txt")));
        assertEquals(removeWhiteSpaces(gson.toJson(commandHandler.getLoghme().finalizeOrder())),removeWhiteSpaces(expected));
    }

    @Test
    public void testFinalize4() throws IOException, ErrorHandler {
        setRestaurantsForFinalizingOrderTest();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        setFoodsForFinalizingOrderTest(4);
        String output = removeWhiteSpaces(gson.toJson(commandHandler.getLoghme().finalizeOrder()));
        assertTrue(output.contains("\"GhormeSabzi\":1"));
        assertTrue(output.contains("\"Kebab\":1"));
        assertTrue(output.contains("\"Gheime\":1"));
    }

    @Test
    public void testFinalizeAll() throws IOException, ErrorHandler {
        setRestaurantsForFinalizingOrderTest();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        setFoodsForFinalizingOrderTest(5);
        String output = removeWhiteSpaces(gson.toJson(commandHandler.getLoghme().finalizeOrder()));
        assertTrue(output.contains("\"Fesenjoon\":2"));
        assertTrue(output.contains("\"TahChin\":4"));
        assertTrue(output.contains("\"ZereshkPolo\":3"));
    }
    @After
    public void finish(){
        commandHandler = null;
    }
}