package ie.CAs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private CommandHandler commandHandler;

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
            return  restaurants;
        }
    }

    public Handler getNearestRestaurants = ctx -> {
        String restaurantsHTML = new String(Files.readAllBytes(Paths.get("src/main/resources/restaurants.html")));
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

    public Handler getRestaurant = ctx -> {};


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.setCommandHandler(server.startServer());
        Javalin app = Javalin.create().start(7000);

        app.get("/restaurants", server.getNearestRestaurants);
        app.get( "/restaurant/:name",server.getRestaurant);


        app.post("/", ctx -> {
            // some code
//            ctx.status(404);
            ctx.result("khar!");
        });
    }

};
