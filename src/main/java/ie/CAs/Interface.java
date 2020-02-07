package ie.CAs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Interface {
    CommandHandler commandHandler;

    public Interface() {
        commandHandler = new CommandHandler();
    }

    public static void main(String[] args) throws java.io.IOException, JsonParseException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while (true){
            line = buff.readLine();
            if (line == null){
                break;
            }
            String[] parsedLine = line.split(" ",2);
            String command = parsedLine[0];
            String data = parsedLine[1];
            Gson gson = new GsonBuilder().create();
            Food food = gson.fromJson(data, Food.class);
            System.out.println(food.getName());
            System.out.println(food.getDescription());
            System.out.println(food.getPopularity());
            System.out.println(food.getPrice());
        }
    }
}

