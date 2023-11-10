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

public class GuideTripController implements Initializable {

    @FXML
    private ListView<String> guideTripsList;

    @FXML
    Label locationInfo, dateInfo, timeInfo, descInfo, priceInfo;

    private JSONParser parser = new JSONParser();
    private JSONObject thisLine;
    private JSONArray jsonList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ReadFromJSON json = new ReadFromJSON();
        jsonList = ReadFromJSON.guideJSON;

        for (int i = 0; i < jsonList.size(); i++) {
            try {
                thisLine = (JSONObject) parser.parse(jsonList.get(i).toString());
            }   catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (!thisLine.isEmpty()) {
                guideTripsList.getItems().add(thisLine.get("location").toString() + ", " + thisLine.get("date") + " Kl: " + thisLine.get("time") + ", ID: " + thisLine.get("id"));

            }
        }
    }


    public void showGuideInfo(){
        int index = guideTripsList.getFocusModel().getFocusedIndex();
        String tripId = guideTripsList.getItems().get(index).split("ID: ")[1];

        for (int i = 0; i < jsonList.size(); i++) {

            try {
                thisLine = (JSONObject) parser.parse(jsonList.get(i).toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (!thisLine.isEmpty()) {
                if (thisLine.get("id").toString().equals(tripId)) {
                    locationInfo.setText(thisLine.get("location").toString());
                    dateInfo.setText(thisLine.get("date").toString());
                    timeInfo.setText(thisLine.get("time").toString());
                    priceInfo.setText("Pris pr/person er: " + thisLine.get("price").toString());
                    descInfo.setText(thisLine.get("description").toString());
                }
            }
        }
    }


    public void deleteGuideTrip() {
        int index = guideTripsList.getFocusModel().getFocusedIndex();
        String tripId = guideTripsList.getItems().get(index).split("ID: ")[1];
        ReadFromJSON json = new ReadFromJSON();
        jsonList = ReadFromJSON.guideJSON;
        JSONArray deleteArray = new JSONArray();

        for (int i = 0; i < jsonList.size(); i++) {
            try {
                thisLine = (JSONObject) parser.parse(jsonList.get(i).toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (!tripId.toString().equals(thisLine.get("id").toString())) {
                deleteArray.add(thisLine);
            }
        }
        try (FileWriter file = new FileWriter("src/main/resources/JSON/Guide.json")) {
            file.write(deleteArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        guideTripsList.getItems().remove(index);
        JSONArray userArray = ReadFromJSON.getUserJSON();
        JSONArray userNewArray = new JSONArray();
        if (!userArray.isEmpty()) {
            for (int i = 0; i < userArray.size(); i++) {
                JSONObject userLine = null;
                try {
                    userLine = (JSONObject) parser.parse(userArray.get(i).toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (!userLine.get("id").toString().equals(tripId.toString())) {
                    userNewArray.add(userLine);
                }
            }
            try (FileWriter file = new FileWriter("src/main/resources/JSON/User.json")) {
                file.write(userNewArray.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        locationInfo.setText("");
        dateInfo.setText("");
        timeInfo.setText("");
        priceInfo.setText("");
        descInfo.setText("");

    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void openGuideView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Guide-View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Guide");
        stage.setScene(scene);
        stage.show();
    }
}