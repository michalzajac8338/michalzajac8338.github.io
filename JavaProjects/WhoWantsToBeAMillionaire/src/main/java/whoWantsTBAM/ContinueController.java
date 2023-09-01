package whoWantsTBAM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

public class ContinueController {


    @FXML
    private ToggleGroup answers;
    @FXML
    public RadioButton YES, NO;
    @FXML
    private Label question, err;
    @FXML
    public Button submit;
    String choice;

    MainApp mainApp = new MainApp();


    public void setLayout() {
        question.setText("You are at: " + MoneyWon.moneyToWin[MoneyWon.index]);
    }

    public void submitAnswer(ActionEvent event) throws IOException {
        try {
            choice = ((RadioButton) answers.getSelectedToggle()).getId();
            mainApp.gameplay(event, choice);
        } catch (NullPointerException e) {
            err.setText("Choose an option");
        }
    }
}