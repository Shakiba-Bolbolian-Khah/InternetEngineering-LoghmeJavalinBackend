package ie.CAs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Interface {
    CommandHandler commandHandler;

    public Interface() {
        commandHandler = new CommandHandler();
    }

    public static void main(String[] args) {
        Interface interFace = new Interface();
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while (true){
            try {
                line = buff.readLine();
            } catch (IOException e) {
                System.out.println("Error Wrong IO Command: Bad IO.");
            }
            if (line == null || line.equals("exit")){
                break;
            }
            String command, data = "";
            try {
                String[] parsedLine = line.split("\\s", 2);
                command = parsedLine[0];
                data = parsedLine[1];
            } catch (ArrayIndexOutOfBoundsException e){
                command = line;
            }
            switch (command) {
                case "addRestaurant":
                    interFace.commandHandler.addRestaurant(data);
                    break;
                case "addFood":
                    interFace.commandHandler.addFood(data);
                    break;
                case "getRestaurants":
                    interFace.commandHandler.getRestaurants();
                    break;
                case "getRestaurant":
                    interFace.commandHandler.getRestaurant(data);
                    break;
                case "getFood":
                    interFace.commandHandler.getFood(data);
                    break;
                case "addToCart":
                    interFace.commandHandler.addToCart(data);
                    break;
                case "getCart":
                    interFace.commandHandler.getCart();
                    break;
                case "finalizeOrder":
                    interFace.commandHandler.finalizeOrder();
                    break;
                case "getRecommendedRestaurants":
                    interFace.commandHandler.getRecommendedRestaurants();
                    break;
                default:
                    System.out.println("Error Wrong IO Command: Command is invalid.");
            }
        }
    }
}