package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIHomepage implements Initializable {

    @FXML
    private void enterMainWindow(ActionEvent e) throws IOException {
        System.out.print("You clicket me!\n");
        Parent homePageParent = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        appStage.hide();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
