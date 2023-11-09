package program.guide;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


    public class guideController implements Initializable {

        @FXML
        private ChoiceBox guideLocationChoice, guidePriceChoice, guideTimeChoice;
        @FXML
        private DatePicker guideDate;
        @FXML
        private TextArea guideDescription;
        private Stage stage;
        private Scene scene;
        private Parent root;
        @FXML
        private AnchorPane guidePublishedPane;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            guideDate.setValue(LocalDate.now());
        }


        public void publishGuide() {
            //String location, String date, String time, String price, String description
            new WriteGuideToJSON(guideLocationChoice.getValue().toString(), guideDate.getValue().toString(), guideTimeChoice.getValue().toString(), guidePriceChoice.getValue().toString(), guideDescription.getText());
            guidePublishedPane.setVisible(true);
        }
        public void closePublishPane(){
            guidePublishedPane.setVisible(false);
        }

        @FXML
        public void openMain(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("Main-View.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Innlogging");
            stage.setScene(scene);
            stage.show();
        }

        public void openGuideTrips(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("GuideTrips-View.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Mine Guide");
            stage.setScene(scene);
            stage.show();
        }
    }

