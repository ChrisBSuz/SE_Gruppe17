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

    public int priceT = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        new ReadFromJSON();
        jsonList = ReadFromJSON.guideJSON;

        for (int i = 0; i < jsonList.size(); i++){
            try {
                thisLine = (JSONObject) parser.parse(jsonList.get(i).toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(!thisLine.isEmpty()) {
                userGuideList.getItems().add(thisLine.get("location").toString() + ", " + thisLine.get("date") + " Kl: " + thisLine.get("time") + ", ID: " + thisLine.get("id"));

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

        String[] fromDate = dateFrom.getValue().toString().split("-");
        String[] toDate = dateTo.getValue().toString().split("-");

        for(int i = 0; i < ReadFromJSON.getGuideJSON().size(); i++) {
            String outputString;
            JSONObject outObj = null;
            try {
                outObj = (JSONObject) parser.parse(ReadFromJSON.getGuideJSON().get(i).toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if(outObj.get("location").toString().equals(userLocationChoice.getValue().toString())) {

                String id = outObj.get("id").toString();
                String l = outObj.get("location").toString();
                String d = outObj.get("date").toString();
                String[] d2 = d.split("-");

                int dateIsInt = Integer.parseInt(d2[0] + d2[1] + d2[2]);

                int dateFromInt = Integer.parseInt(fromDate[0] + fromDate[1] + fromDate[2]);
                int dateToInt = Integer.parseInt(toDate[0] + toDate[1] + toDate[2]);


                String t = outObj.get("time").toString();

                if(dateIsInt >= dateFromInt && dateIsInt <= dateToInt){
                    outputString = l + " , " + d + " kl: " + t + ", ID: " + id;
                    userGuideList.getItems().add(counter, outputString);

                    counter++;


                }

            }

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
        new WriteUserToJSON(tripId, persons.getValue().toString());
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