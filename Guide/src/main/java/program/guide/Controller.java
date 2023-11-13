package program.guide;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    public void openGuideView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Guide-View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Opprett Tur");
        stage.setScene(scene);
        stage.show();
    }

    public void openUser(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("User-View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Søk");
        stage.setScene(scene);
        stage.show();
    }

}