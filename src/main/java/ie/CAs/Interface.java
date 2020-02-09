package ie.CAs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Interface {
    CommandHandler commandHandler;

    public Interface() {
        commandHandler = new CommandHandler();
    }

    public static void main(String[] args) {
        Interface interFace = new Interface();
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
//        while (true){
//            try {
//                line = buff.readLine();
//            } catch (IOException e) {
//                System.out.println("Error Wrong IO Command: Bad IO.");
//            }
//            if (line == null || line.equals("exit")){
//                break;
//            }
//            String command, data = "";
//            try {
//                String[] parsedLine = line.split("\\s", 2);
//                command = parsedLine[0];
//                data = parsedLine[1];
//            } catch (ArrayIndexOutOfBoundsException e){
//                command = line;
//            }
//            switch (command) {
//                case "addRestaurant":
//                    interFace.commandHandler.addRestaurant(data);
//                    break;
//                case "addFood":
//                    interFace.commandHandler.addFood(data);
//                    break;
//                case "getRestaurants":
//                    interFace.commandHandler.getRestaurants();
//                    break;
//                case "getRestaurant":
//                    interFace.commandHandler.getRestaurant(data);
//                    break;
//                case "getFood":
//                    interFace.commandHandler.getFood(data);
//                    break;
//                case "addToCart":
//                    interFace.commandHandler.addToCart(data);
//                    break;
//                case "getCart":
//                    interFace.commandHandler.getCart();
//                    break;
//                case "finalizeOrder":
//                    interFace.commandHandler.finalizeOrder();
//                    break;
//                case "getRecommendedRestaurants":
//                    interFace.commandHandler.getRecommendedRestaurants();
//                    break;
//                default:
//                    System.out.println("Error Wrong IO Command: Command is invalid.");
//            }
//        }
        Loghme l = new Loghme();
        ArrayList<Food> menu = new ArrayList<Food>();
        ArrayList<Food> menu1 = new ArrayList<Food>();
        ArrayList<Food> menu2 = new ArrayList<Food>();
        ArrayList<Food> menu3 = new ArrayList<Food>();
        Food f = new Food("f","2",3,4);
        Food f1 = new Food("f1","3",6,6);
        menu.add(f);
        System.out.println("Size"+menu.size());
        Restaurant r1 = new Restaurant("r1","b",new Location(1,2),menu);
        System.out.println(r1.addFood(f1));
        System.out.println("Size"+menu.size());
        System.out.println(l.addRestaurant(r1));
//        menu1.add(f1);
        Restaurant r2 = new Restaurant("r2","d",new Location(3,4),menu1);
        System.out.println(l.addRestaurant(r2));
        Food f3 = new Food("f3","3",688,65);
        Restaurant r3 = new Restaurant("r3","d",new Location(3,4),menu2);
        System.out.println(r3.addFood(f3));
        System.out.println("Size"+menu.size());
        System.out.println(r3.addFood(f1));
        System.out.println(l.addRestaurant(r3));
        Restaurant r4 = new Restaurant("r4","d",new Location(3,4),menu3);
        System.out.println(r4.addFood(f3));
        System.out.println(r4.addFood(f1));
        System.out.println(l.addRestaurant(r4));
        System.out.println(l.addToCart("r1","f1"));
        System.out.println(l.addToCart("r1","f1"));
        System.out.println(l.addToCart("r1","f"));
        System.out.println(l.getRecommendedRestaurants());
        System.out.println(l.getCart());
//        System.out.println(l.getRestaurant("a"));
        System.out.println(l.finalizeOrder());


    }
}