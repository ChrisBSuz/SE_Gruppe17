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
        System.out.println("open guide view");
    }

    public void openUser(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("User-View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Søk");
        stage.setScene(scene);
        stage.show();
        System.out.println("open User view");

    }

    @FXML
    public void openAdminView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Admin-View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Administrator");
        stage.setScene(scene);
        stage.show();
        System.out.println("open admin view");
    }

    public void openMainView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Main-View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Brukervalg");
        stage.setScene(scene);
        stage.show();
        System.out.println("open main view");
    }

}