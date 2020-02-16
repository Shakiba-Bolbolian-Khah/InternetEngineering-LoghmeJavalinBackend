package ie.CAs;

import org.junit.*;
import java.io.*;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Phase2Test {
    Server server;

    public String removeWhiteSpaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    @Before
    public void setup() throws IOException {
        server = new Server();
        server.setCommandHandler(server.startServer());
    }

    @Test (expected = ErrorHandler.class)
    public void testGetRestaurant0() throws ErrorHandler {
        server.getCommandHandler().getLoghme().getRestaurant("mytest");
    }

    @Test (expected = ErrorHandler.class)
    public void testGetRestaurant1() throws IOException, ErrorHandler {
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/testRecommended.txt")));
        server.getCommandHandler().addRestaurant(buff.readLine());
        server.getCommandHandler().getLoghme().getRestaurant("per");
    }

    @Test
    public void testGetRestaurant2() throws IOException, ErrorHandler {
        Restaurant testRestaurant = server.getCommandHandler().getLoghme().getRestaurants().get(0);
        assertEquals(server.getCommandHandler().getLoghme().getRestaurant(testRestaurant.getId()).getName(),testRestaurant.getName());
    }

    @Test (expected = ErrorHandler.class)
    public void testFinalize0() throws ErrorHandler {
        server.getCommandHandler().getLoghme().finalizeOrder();
    }

    @Test (expected = ErrorHandler.class)
    public void testFinalize1() throws ErrorHandler {
        Restaurant testRestaurant = server.getCommandHandler().getLoghme().getRestaurants().get(0);
        Food testFood = testRestaurant.getMenu().get(0);
        server.getCommandHandler().getLoghme().addToCart(testRestaurant.getId(), testFood.getName());
        server.getCommandHandler().getLoghme().finalizeOrder();
    }

    @Test (expected = ErrorHandler.class)
    public void testFinalize2() throws ErrorHandler {
        Restaurant testRestaurant1 = server.getCommandHandler().getLoghme().getRestaurants().get(0);
        Food testFood1 = testRestaurant1.getMenu().get(0);
        server.getCommandHandler().getLoghme().addToCart(testRestaurant1.getId(), testFood1.getName());

        Restaurant testRestaurant2 = server.getCommandHandler().getLoghme().getRestaurants().get(1);
        Food testFood2 = testRestaurant2.getMenu().get(0);
        server.getCommandHandler().getLoghme().addToCart(testRestaurant2.getId(), testFood2.getName());
    }

    @Test
    public void testFinalize3() throws ErrorHandler {
        Restaurant testRestaurant = server.getCommandHandler().getLoghme().getRestaurants().get(0);
        Food testFood = testRestaurant.getMenu().get(0);
        server.getCommandHandler().getLoghme().addToCart(testRestaurant.getId(), testFood.getName());
        server.getCommandHandler().getLoghme().increaseCredit(100000);
        Map<String, Integer> testCart = server.getCommandHandler().getLoghme().finalizeOrder();
        assertTrue(testCart.containsKey(testFood.getName()));
    }
}