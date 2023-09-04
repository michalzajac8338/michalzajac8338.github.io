package whoWantsTBAM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SetupController {
    FXMLLoader loader;
    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private TextField textField;
    @FXML
    public Button submit;

    @FXML
    protected void submit(ActionEvent event) throws IOException {
        MainApp.name = String.valueOf(textField.getText());

        loader = new FXMLLoader(getClass().getResource("questions.fxml"));
        root = loader.load();

        QuestionsController controller2 = loader.getController();
        controller2.setLayout("Normal","");

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(MainApp.css);
        stage.setScene(scene);
        stage.show();
    }
}