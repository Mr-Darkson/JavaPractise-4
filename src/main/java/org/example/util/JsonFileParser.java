package org.example.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class JsonFileParser {

    public static HashMap<String, Integer> parse() {

        HashMap<String, Integer> data = new HashMap<>();
        JSONParser parser = new JSONParser();

        try(FileReader reader = new FileReader("test.json")) {
            JSONObject rootJsonObject  = (JSONObject) parser.parse(reader);

            int passengers = Integer.parseInt((String) rootJsonObject.get("passengers"));
            int floors = Integer.parseInt((String) rootJsonObject.get("floors"));
            int elevators = Integer.parseInt((String) rootJsonObject.get("elevators"));
            int outputDelayInMs = Integer.parseInt((String) rootJsonObject.get("outputDelayInMs"));
            int generationLowerBoundDelay = Integer.parseInt((String) rootJsonObject.get("generationLowerBoundDelay"));
            int generationUpperBoundDelay = Integer.parseInt((String) rootJsonObject.get("generationUpperBoundDelay"));
            int numberOfStages = Integer.parseInt((String) rootJsonObject.get("numberOfStages"));

            data.put("passengers",passengers);
            data.put("floors",floors);
            data.put("elevators",elevators);
            data.put("outputDelayInMs",outputDelayInMs);
            data.put("generationLowerBoundDelay",generationLowerBoundDelay);
            data.put("generationUpperBoundDelay",generationUpperBoundDelay);
            data.put("numberOfStages",numberOfStages);
        } catch (IOException e) {
            System.out.printf("Parsing error" + e.toString());
        } catch (ParseException e) {
            System.out.printf("Parsing error" + e.toString());
        }


        return data;
    }
}
