package ie.CAs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Server {
    private CommandHandler commandHandler;

    public String readHTMLFile(String filePath) throws IOException {
        String HTMLFile = "";
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        while (true){
            String inline = buff.readLine();
            if (inline == null){
                break;
            }
            HTMLFile += inline;
        }
        return HTMLFile;
    }

    public void setCommandHandler(ArrayList<Restaurant> restaurants) {
        commandHandler = new CommandHandler(restaurants);
    }

    public ArrayList<Restaurant> startServer() throws IOException {
        URL url = new URL("http://138.197.181.131:8080/restaurants");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        String inline = "";
        if(responsecode != 200)
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        else {
            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext()) {
                inline += sc.nextLine();
            }
            ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            restaurants = gson.fromJson(inline, new TypeToken<ArrayList<Restaurant>>(){}.getType());
            sc.close();
            System.out.println(inline);
            return  restaurants;
        }
    }

    public Handler getNearestRestaurants = ctx -> {
        String restaurantsHTML = readHTMLFile("src/main/resources/restaurants.html");
        ArrayList<Restaurant> restaurants = this.commandHandler.getLoghme().getRestaurants();

        for(Restaurant restaurant: restaurants){
            restaurantsHTML += "<tr>\n" +
                    "            <td>"+restaurant.getId()+"</td>\n" +
                    "            <td><img class=\"logo\" src=\""+restaurant.getLogoUrl()+"\" alt=\"logo\"></td>\n" +
                    "            <td>"+restaurant.getName()+"</td>\n" +
                    "        </tr>";
        }
        restaurantsHTML += "</table>\n</body>\n</html>";

        ctx.html(restaurantsHTML);
        ctx.status(200);
    };

    public Handler getRestaurant = ctx -> {
        Restaurant restaurant = null;
        String restaurantHTML = readHTMLFile("src/main/resources/restaurant.html");
        String restaurantId = ctx.pathParam("id");
        if (restaurantId.equals("null"))
            return;
        try {
            restaurant = commandHandler.getLoghme().getRestaurant(restaurantId);
        } catch (ErrorHandler e){
            if(e.getMessage().equals("403")) {
                restaurantHTML = readHTMLFile("src/main/resources/403Error.html");
                ctx.html(restaurantHTML);
                ctx.status(403);
            }
            else if(e.getMessage().equals("404")){
                restaurantHTML = readHTMLFile("src/main/resources/404Error.html");
                ctx.html(restaurantHTML);
                ctx.status(404);
            }
            return;
        }
        restaurantHTML += "<li>id: "+restaurant.getId()+"</li>\n" +
                "        <li>name: "+restaurant.getName()+"</li>\n" +
                "        <li>location: ("+restaurant.getLocation().getX()+", "+restaurant.getLocation().getY()+")</li>\n" +
                "        <li>logo: <img src=\""+restaurant.getLogoUrl()+"\" alt=\"logo\"></li>\n" +
                "        <li>menu: \n" +
                "        \t<ul>";
        for(Food food: restaurant.getMenu()){
            restaurantHTML += "<li>\n" +
                "                    <img src=\""+food.getImageUrl()+"\" alt=\"logo\">\n" +
                "                    <div>"+food.getName()+"</div>\n" +
                "                    <div>"+food.getPrice()+" Toman</div>\n" +
                "                    <form action=\"\" method=\"POST\">\n" +
                "                        <input type=\"hidden\" id=\"foodName\" name=\"foodName\" value=\""+food.getName()+"\">\n" +
                "                        <input type=\"submit\" value=\"addToCart\">"+
                "                    </form>\n" +
                "                </li>";
        }
        restaurantHTML += "</ul>\n" +
                "        </li>\n" +
                "    </ul>\n" +
                "</body>\n" +
                "</html>";

        ctx.html(restaurantHTML);
        ctx.status(200);
    };

    public Handler getUser = ctx -> {
        String userHTML = readHTMLFile("src/main/resources/user.html");
        User user = commandHandler.getLoghme().getUser();
        userHTML +="<li>id: 1</li>\n" +
                    "        <li>full name: "+user.getFirstName()+" "+user.getLastName()+"</li>\n" +
                "        <li>phone number: "+user.getPhoneNumber()+"</li>\n" +
                "        <li>email: "+user.getEmail()+"</li>\n" +
                "        <li>credit: "+user.getCredit()+" Toman</li>\n" +
                "        <form action=\"\" method=\"POST\">\n" +
                "            <button type=\"submit\">increase</button>\n" +
                "            <input type=\"text\" name=\"credit\" value=\"\" />\n" +
                "        </form>\n" +
                "    </ul>\n" +
                "</body>\n" +
                "</html>";
        ctx.html(userHTML);
        ctx.status(200);
    };

    public Handler getCart = ctx -> {
        String cartHTML = readHTMLFile("src/main/resources/cart.html");
        try {
            cartHTML += "<div>"+commandHandler.getLoghme().getUser().getShoppingCart().getRestaurantName()+"</div>\n <ul>";
            Map<String,Integer> cart = commandHandler.getLoghme().getCart();
            for(Map.Entry<String,Integer> entry: cart.entrySet())
                cartHTML += "<li>"+entry.getValue()+": "+entry.getKey()+"</li>";

            cartHTML +="</ul>\n" +
                    "    <form action=\"\" method=\"POST\">\n" +
                    "        <button type=\"submit\">finalize</button>\n" +
                    "    </form>\n" +
                    "</body>\n" +
                    "</html>";
            ctx.html(cartHTML);
            ctx.status(200);
        }
        catch (ErrorHandler e){
            if(e.getMessage().equals("Error: There is nothing to show in your cart!")){
                cartHTML = readHTMLFile("src/main/resources/cartEmpty.html");
                ctx.html(cartHTML);
                ctx.status(200);
            }
        }
    };

    public Handler increaseCredit = ctx -> {
        String creditHTML = readHTMLFile("src/main/resources/increaseCredit.html");
        int credit = Integer.parseInt(ctx.formParam("credit"));
        if(commandHandler.getLoghme().increaseCredit(credit).equals("Credit increased successfully!")){
            creditHTML += commandHandler.getLoghme().getUser().getCredit();
            creditHTML += "</h2>\n</body>\n</html>";
        }
        ctx.html(creditHTML);
        ctx.status(200);
    };

    public Handler addToCart = ctx -> {
        Restaurant restaurant;
        String restaurantHTML = readHTMLFile("src/main/resources/restaurant.html");
        String restaurantId = ctx.pathParam("id");
        String foodName = ctx.formParam("foodName");
        if (restaurantId.equals("null"))
            return;
        try {
            restaurant = commandHandler.getLoghme().getRestaurant(restaurantId);
            restaurantHTML += "<li>id: " + restaurant.getId() + "</li>\n" +
                    "        <li>name: " + restaurant.getName() + "</li>\n" +
                    "        <li>location: (" + restaurant.getLocation().getX() + ", " + restaurant.getLocation().getY() + ")</li>\n" +
                    "        <li>logo: <img src=\"" + restaurant.getLogoUrl() + "\" alt=\"logo\"></li>\n" +
                    "        <li>menu: \n" +
                    "        \t<ul>";
            for (Food food : restaurant.getMenu()) {
                restaurantHTML += "<li>\n" +
                        "                    <img src=\"" + food.getImageUrl() + "\" alt=\"logo\">\n" +
                        "                    <div>" + food.getName() + "</div>\n" +
                        "                    <div>" + food.getPrice() + " Toman</div>\n" +
                        "                    <form action=\"\" method=\"POST\">\n" +
                        "                        <input type=\"hidden\" id=\"foodName\" name=\"foodName\" value=\"" + food.getName() + "\">\n" +
                        "                        <input type=\"submit\" value=\"addToCart\">" +
                        "                    </form>\n" +
                        "                </li>";
            }
            restaurantHTML += "</ul>\n" +
                    "        </li>\n" +
                    "    </ul>\n" +
                    "<h3> " + commandHandler.getLoghme().addToCart(restaurantId, foodName) + " </h3>\n" +
                    "</body>\n" +
                    "</html>";
        } catch (ErrorHandler e){
            if(e.getMessage().equals("403")) {
                restaurantHTML = readHTMLFile("src/main/resources/403Error.html");
                ctx.html(restaurantHTML);
                ctx.status(403);
            }
            return;
        }
        ctx.html(restaurantHTML);
        ctx.status(200);
    };

    public Handler finalizeOrder = ctx -> {
        String cartHTML = readHTMLFile("src/main/resources/cart.html");
        try {
            cartHTML += "<div>"+commandHandler.getLoghme().getUser().getShoppingCart().getRestaurantName()+"</div>\n <ul>";
            Map<String,Integer> cart = commandHandler.getLoghme().finalizeOrder();
            for(Map.Entry<String,Integer> entry: cart.entrySet())
                cartHTML += "<li>"+entry.getValue()+": "+entry.getKey()+"</li>";

            cartHTML +="</ul>\n" + "<h3>Your order finalized successfully!</h3>\n</body>\n</html>";
            ctx.html(cartHTML);
            ctx.status(200);
        }
        catch (ErrorHandler e){
            cartHTML = readHTMLFile("src/main/resources/400Error.html");
            if(e.getMessage().equals("400-1")){
                cartHTML += "<h3 align=\"center\">Your shopping cart is empty!</h3>\n </body>\n </html>";
                ctx.html(cartHTML);
                ctx.status(400);
            }
            else if(e.getMessage().equals("400-2")){
                cartHTML += "<h3 align=\"center\">You don't have enough credit!</h3>\n </body>\n </html>";
                ctx.html(cartHTML);
                ctx.status(400);
            }
            return;
        }
    };

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.setCommandHandler(server.startServer());
        Javalin app = Javalin.create().start(7000);

        app.get("/restaurant/:id", server.getRestaurant);
        app.get("/restaurants", server.getNearestRestaurants);
        app.get("/user", server.getUser);
        app.get("/cart", server.getCart);
        app.post("/user", server.increaseCredit);
        app.post("/restaurant/:id", server.addToCart);
        app.post("/cart", server.finalizeOrder);
    }

};
