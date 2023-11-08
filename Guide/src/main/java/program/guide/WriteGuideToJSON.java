package program.guide;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WriteGuideToJSON {
    public WriteGuideToJSON(String location, String date, String time, String price, String description) {

        JSONArray array = new JSONArray();


        JSONObject obj = new JSONObject();
        obj.put("location", location);
        obj.put("date", date.toString());
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

        JSONParser parser = new JSONParser();
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


}


