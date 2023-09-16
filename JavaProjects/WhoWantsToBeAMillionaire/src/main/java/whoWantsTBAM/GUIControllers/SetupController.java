package whoWantsTBAM.GUIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whoWantsTBAM.MainApp;
import whoWantsTBAM.model.Player;

import java.io.IOException;

public class SetupController {

    @FXML
    private TextField textField;
    @FXML
    public Button submit;

    @FXML
    protected void submit(ActionEvent event) throws IOException {
        MainApp.name = String.valueOf(textField.getText());
        MainApp.player = new Player(MainApp.name);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/whoWantsTBAM/Scenes/questions.fxml"));
        Parent root = loader.load();

        QuestionsController controller2 = loader.getController();
        controller2.setLayout("Normal","");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApp.css);
        stage.setScene(scene);
        stage.show();
    }
}