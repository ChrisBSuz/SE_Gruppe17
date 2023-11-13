package program.guide;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFromJSON {



    public static JSONArray guideJSON;
    public static JSONArray turistJSON;

    public ReadFromJSON(){
        JSONParser parser = new JSONParser();

        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get("Guide/src/main/resources/JSON/Guide.json")));
        } catch (IOException e) {throw new RuntimeException(e);}

        try {
            guideJSON = (JSONArray) (parser).parse(json);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            json = new String(Files.readAllBytes(Paths.get("Guide/src/main/resources/JSON/User.json")));

        } catch (IOException e) {throw new RuntimeException(e);}

        if(!json.isBlank()){
            try {
                turistJSON = (JSONArray) (parser).parse(json);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }}



    }

    public static JSONArray getGuideJSON() {
        return guideJSON;
    }

    public static JSONArray getUserJSON() {
        return turistJSON;
    }
}
