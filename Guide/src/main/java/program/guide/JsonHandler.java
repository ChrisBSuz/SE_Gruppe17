package guide.program.guide;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonHandler {
    private final JSONParser parser = new JSONParser();
    String file = null;
    JSONArray json;
    JSONObject thisLine;


    public void writeGuideToJson(String location, String date, String time, String price, String description){

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();

        obj.put("location", location);
        obj.put("date", date);
        obj.put("time", time);
        obj.put("price", price);
        obj.put("description", description);
        obj.put("id", 1);


        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get("src/main/resources/JSON/Guide.json")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONArray oldArray = null;
        JSONObject oldObject = new JSONObject();
        try {
            if(!json.isBlank()){

                oldArray = (JSONArray) parser.parse(json);

                for(int i = 0; i < oldArray.size(); i++){
                    // array.add(oldArray.get(i));
                    oldObject = (JSONObject) parser.parse(oldArray.get(i).toString());
                    obj.put("id",Integer.parseInt(oldObject.get("id").toString())+1);

                    array.add(oldObject);


                }

            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        array.add(obj);

        try (
                FileWriter file = new FileWriter("src/main/resources/JSON/Guide.json")) {
            file.write(array.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeUserToJson(String id,String persons){
        JSONArray array = new JSONArray();
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

    public JSONArray readFromJson(String path){

        String userPath = "src/main/resources/JSON/User.json";
        String guidePath = "src/main/resources/JSON/Guide.json";
        String thisPath = switch (path) {
            case "user" -> userPath;
            case "guide" -> guidePath;
            default -> "";
        };

        try {
            file = new String(Files.readAllBytes(Paths.get(thisPath)));
        } catch (IOException e) {throw new RuntimeException(e);}

        try {
            json = (JSONArray) (parser).parse(file);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return json;
    }


    public void deleteGuideFromJson(String id){
        JSONArray jsonList = readFromJson("guide");
        JSONArray deleteArray = new JSONArray();
        for (int i = 0; i < jsonList.size(); i++){
            try {
                thisLine = (JSONObject) parser.parse(jsonList.get(i).toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if(!id.equals(thisLine.get("id").toString())){
                deleteArray.add(thisLine);
            }
        }
        try (
                FileWriter file = new FileWriter("src/main/resources/JSON/Guide.json")) {
            file.write(deleteArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteUserFromJson(id);


    }
    public void deleteUserFromJson(String id){
        JSONArray userArray = readFromJson("user");
        JSONArray userNewArray = new JSONArray();
        if(!userArray.isEmpty()) {
            for (int i = 0; i < userArray.size(); i++) {
                JSONObject userLine = null;
                try {
                    userLine = (JSONObject) parser.parse(userArray.get(i).toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (!userLine.get("id").toString().equals(id)) {
                    userNewArray.add(userLine);
                }
            }
            try (
                    FileWriter file = new FileWriter("src/main/resources/JSON/User.json")) {
                file.write(userNewArray.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



}


