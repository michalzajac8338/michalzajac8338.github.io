package whoWantsTBAM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GoodbyeController {


    @FXML
    private Label label1, label2, label3;
    @FXML
    public Button submit;
    Stage stage;

    public void setLayout(String s, String s1) {
        label1.setText("It was a nice game, " + MainApp.name);
        label2.setText(s);
        label3.setText(s1);
    }

    public void submitAnswer(ActionEvent event) {

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }
}