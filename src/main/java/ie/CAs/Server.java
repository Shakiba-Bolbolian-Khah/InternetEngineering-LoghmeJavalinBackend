package ie.CAs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.setCommandHandler(server.startServer());

//            Javalin app = Javalin.create().start(7000);
//            app.get("/", ctx -> ctx.result("Hello World"));
    }

};
