package program.guide;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WriteUserToJSON {
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WriteUserToJSON {

    public WriteUserToJSON(String id, String persons){
        JSONArray array = new JSONArray();
        JSONParser parser = new JSONParser();
        JSONArray oldArray = null;
        JSONObject obj = new JSONObject();
        boolean check = true;
        JSONObject thisLine;
        obj.put("id", id);
        obj.put("persons",persons);



        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get("src/main/resources/JSON/User.json")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(!json.isBlank()){

            try {
                oldArray = (JSONArray) parser.parse(json);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            for(int i = 0; i < oldArray.size(); i++){
                try {
                    thisLine = (JSONObject) parser.parse(oldArray.get(i).toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if(!thisLine.get("id").equals(id)){
                    array.add(oldArray.get(i));

                }
                else {
                    array.add(obj);
                    check = false;
                }

            }
            if(check){
                array.add(obj);
            }
        }
        try (
                FileWriter file = new FileWriter("src/main/resources/JSON/User.json")) {
            file.write(array.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}


    public WriteUserToJSON(String id, String persons){
        JSONArray array = new JSONArray();
        JSONParser parser = new JSONParser();
        JSONArray oldArray = null;
        JSONObject obj = new JSONObject();
        boolean check = true;
        JSONObject thisLine;
        obj.put("id", id);
        obj.put("persons",persons);



        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get("src/main/resources/JSON/User.json")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(!json.isBlank()){

            try {
                oldArray = (JSONArray) parser.parse(json);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            for(int i = 0; i < oldArray.size(); i++){
                try {
                    thisLine = (JSONObject) parser.parse(oldArray.get(i).toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if(!thisLine.get("id").equals(id)){
                    array.add(oldArray.get(i));

                }
                else {
                    array.add(obj);
                    check = false;
                }

            }
            if(check){
                array.add(obj);
            }
        }
        try (
                FileWriter file = new FileWriter("src/main/resources/JSON/User.json")) {
            file.write(array.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}

