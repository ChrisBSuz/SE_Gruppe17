package program.guide;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserTripController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    ListView<String> userTripList;
    @FXML
    Label locationInfo, dateInfo, timeInfo, descInfo, priceInfo, totalPriceInfo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new ReadFromJSON();
        JSONArray arrayUser = ReadFromJSON.getUserJSON();
        JSONArray arrayGuide = ReadFromJSON.getGuideJSON();
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        JSONObject obj1 = null;
        if (!arrayUser.isEmpty() && !arrayGuide.isEmpty()) {


            for (int i = 0; i < arrayGuide.size(); i++) {
                obj = (JSONObject) arrayGuide.get(i);
                for (int x = 0; x < arrayUser.size(); x++) {
                    obj1 = (JSONObject) arrayUser.get(x);
                    if (obj.get("id").toString().equals(obj1.get("id").toString())) {
                        userTripList.getItems().add(obj.get("location").toString() + ", " + obj.get("date") + " Kl: " + obj.get("time") + ", ID: " + obj.get("id"));
                    }
                }
            }
        }

    }

    public void deleteUserTrip(){
        int index = userTripList.getFocusModel().getFocusedIndex();
        String tripId = userTripList.getItems().get(index).split("ID: ")[1];
        new ReadFromJSON();
        JSONParser parser = new JSONParser();
        JSONArray arrayUser = ReadFromJSON.getUserJSON();
        JSONArray newArray = new JSONArray();

        if(!arrayUser.isEmpty()) {
            for (int i = 0; i < arrayUser.size(); i++) {
                JSONObject thisline = null;
                try {
                    thisline = (JSONObject) parser.parse(arrayUser.get(i).toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (!tripId.toString().equals(thisline.get("id").toString())) {
                    newArray.add(thisline);
                }

            }
            try (
                    FileWriter file = new FileWriter("src/main/resources/JSON/User.json")) {
                file.write(newArray.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            userTripList.getItems().remove(index);
            locationInfo.setText("");
            dateInfo.setText("");
            timeInfo.setText("");
            descInfo.setText("");
            priceInfo.setText("");
            totalPriceInfo.setText("");
        }


    }

    public void showUserTripInfo(){
        int index = userTripList.getFocusModel().getFocusedIndex();
        String tripId = userTripList.getItems().get(index).split("ID: ")[1];
        new ReadFromJSON();
        JSONArray arrayUser = ReadFromJSON.getUserJSON();
        JSONArray arrayGuide = ReadFromJSON.getGuideJSON();
        JSONParser parser = new JSONParser();
        int pricePerPerson = 0;
        for(int i = 0; i < arrayGuide.size(); i++){
            JSONObject thisline = null;
            try {
                thisline = (JSONObject) parser.parse(arrayGuide.get(i).toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(tripId.toString().equals(thisline.get("id").toString())){
                // locationInfo, dateInfo, timeInfo, descInfo, priceInfo, totalPriceInfo;'
                locationInfo.setText(thisline.get("location").toString());
                dateInfo.setText(thisline.get("date").toString());
                timeInfo.setText(thisline.get("time").toString());
                descInfo.setText(thisline.get("description").toString());
                priceInfo.setText("pris pr/person: " + thisline.get("price"));
                pricePerPerson = Integer.parseInt(thisline.get("price").toString());
            }


        }
        for(int x = 0; x < arrayUser.size(); x++){
            JSONObject thisline = null;
            try {
                thisline = (JSONObject) parser.parse(arrayUser.get(x).toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if(tripId.toString().equals(thisline.get("id").toString())){
                String totalPrice = String.valueOf((Integer.parseInt(thisline.get("persons").toString())*pricePerPerson));
                totalPriceInfo.setText("Anntal personer registrert: " + thisline.get("persons").toString() + "\n" + "Total pris er: " + totalPrice);
            }

        }

    }

    @FXML
    public void openUserView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("User-View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Søk");
        stage.setScene(scene);
        stage.show();
    }
}
