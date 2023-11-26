package program.guide;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    ListView<String> userGuideList;
    private JSONParser parser = new JSONParser();
    private JSONObject thisLine;
    private JSONArray jsonList;
    private  String tripId;

    @FXML
    Button signUpButton;
    @FXML
    ChoiceBox persons, userLocationChoice;
    @FXML
    DatePicker dateFrom, dateTo;
    @FXML
    Label userLocationInfo, userDateInfo, userTimeInfo, userDescInfo, pricePerPerson, signupUserToGuideLabel;
    @FXML
    AnchorPane signupUserToGuidePane, paymentFailedPane;
    JsonHandler jsonHandler = new JsonHandler();

    public int priceT = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        jsonList = jsonHandler.readFromJson("guide");

        for (int i = 0; i < jsonList.size(); i++){

            try {
                thisLine = (JSONObject) parser.parse(jsonList.get(i).toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if(thisLine.get("description").equals("dette er en test!!!!!")){
                jsonHandler.deleteGuideFromJson(thisLine.get("id").toString());
            }
            else if(!thisLine.isEmpty()) {
                userGuideList.getItems().add(searchResult("",i,"0000-00-00", "9999-99-99"));

            }
        }
        dateFrom.setValue(LocalDate.now());
        dateTo.setValue(LocalDate.now());
    }

    public void showUserGuideInfo(){
        int index = userGuideList.getFocusModel().getFocusedIndex();
        tripId = userGuideList.getItems().get(index).split("ID: ")[1];

        for(int i = 0; i < jsonList.size(); i++){
            try {
                thisLine = (JSONObject) parser.parse(jsonList.get(i).toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(!thisLine.isEmpty()) {
                if(thisLine.get("id").toString().equals(tripId)){
                    userLocationInfo.setText(thisLine.get("location").toString());
                    userDateInfo.setText(thisLine.get("date").toString());
                    userTimeInfo.setText(thisLine.get("time").toString());
                    pricePerPerson.setText("Pris pr/person er: " + thisLine.get("price").toString());
                    userDescInfo.setText(thisLine.get("description").toString());
                    persons.setVisible(true);
                    signUpButton.setVisible(true);
                    priceT = Integer.parseInt(thisLine.get("price").toString());

                }

            }
        }

    }

    public void searchForGuide(){
        userGuideList.getItems().clear();

        userLocationInfo.setText("");
        userDateInfo.setText("");
        userTimeInfo.setText("");
        pricePerPerson.setText("");
        userDescInfo.setText("");
        persons.setVisible(false);
        signUpButton.setVisible(false);

        int counter = 0;

        for(int i = 0; i < jsonList.size(); i++) {
            String result = searchResult(userLocationChoice.getValue().toString(),i, dateFrom.getValue().toString(),dateTo.getValue().toString());

            if(!result.isBlank()){
                userGuideList.getItems().add(result);
            }


        }


    }


    public boolean fromDateToDate(String dateFrom, String dateTo, String thisDate){
        String[] fromDate = dateFrom.split("-");
        String[] toDate = dateTo.split("-");
        String[] dateThis = thisDate.split("-");

        int dateIsInt = Integer.parseInt(dateThis[0] + dateThis[1] + dateThis[2]);
        int dateFromInt = Integer.parseInt(fromDate[0] + fromDate[1] + fromDate[2]);
        int dateToInt = Integer.parseInt(toDate[0] + toDate[1] + toDate[2]);

        if(dateIsInt >= dateFromInt && dateIsInt <= dateToInt) {

            return true;
        }
        else {
            return false;
        }
    }

    public String searchResult(String location,int count,String dateFrom, String dateTo) {
        String outputString;
        JSONObject outObj;
        jsonList = jsonHandler.readFromJson("guide");

        try {
            outObj = (JSONObject) parser.parse(jsonList.get(count).toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String id = outObj.get("id").toString();
        String l = outObj.get("location").toString();
        String d = outObj.get("date").toString();
        String t = outObj.get("time").toString();
        if (location.isBlank()) {
            if(!outObj.get("description").equals("dette er en test!!!!!")){
                return l + ", " + d + " kl: " + t + ", ID: " + id;
            }
            else {
                return "";
            }

        } else {

            if (location.equals(l)) {

                if (fromDateToDate(dateFrom, dateTo, d)) {
                    outputString = l + ", " + d + " kl: " + t + ", ID: " + id;

                    return outputString;

                }
            }
            return "";
        }
    }

    public void showSignupInfo(){
        signupUserToGuidePane.setVisible(true);
        paymentFailedPane.setVisible(false);
        signupUserToGuideLabel.setText("Denne turen vil koste deg totalt: " + priceT*Integer.parseInt(persons.getValue().toString()) + " KR");
    }
    public void hideSignupInfo(){
        signupUserToGuidePane.setVisible(false);
        paymentFailedPane.setVisible(false);
    }
    public void assignUserToGuide(){
        jsonHandler.writeUserToJson(tripId, persons.getValue().toString());
        signupUserToGuidePane.setVisible(false);
    }
    public void paymentFailed(){
        signupUserToGuidePane.setVisible(false);
        paymentFailedPane.setVisible(true);
    }
    public void openMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Main-View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Innlogging");
        stage.setScene(scene);
        stage.show();
    }
    public void openUserTrip(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("UserTrip-View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Mine Turer");
        stage.setScene(scene);
        stage.show();
    }
}
