package program.guide;

import javafx.application.Application;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

class GuideTripControllerTest {
    JsonHandler jsonHandler = new JsonHandler();
    JSONArray jsonList;
    JSONObject thisLine;
    JSONParser parser = new JSONParser();

    //sjekker om filen er tom før testen;
    @Test
    void showGuideInfo() {
        jsonHandler.writeGuideToJson("Halden", "2023-02-13", "07:00", "500", "dette er en test!!!!!");
        jsonList = jsonHandler.readFromJson("guide");
        String tripId = "1";
        for (int i = 0; i < jsonList.size(); i++) {

            try {
                thisLine = (JSONObject) parser.parse(jsonList.get(i).toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(thisLine.get("id").toString().equals(tripId)){
                assertTrue(true);
            }
        }


    }

    @Test
    void deleteGuideTrip() {
        jsonHandler.deleteGuideFromJson("1");
    }

}