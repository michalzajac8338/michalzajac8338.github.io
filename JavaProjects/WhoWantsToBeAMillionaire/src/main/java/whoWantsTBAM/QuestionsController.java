package whoWantsTBAM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class QuestionsController {


    @FXML
    private ToggleGroup answers;
    @FXML
    public RadioButton A, B, C, D, ATA, FF;
    @FXML
    private Label question, err;
    @FXML
    public Button submit;

    MainApp mainApp = new MainApp();


    public void setLayout(String option) {
        question.setText(Questions.currentQuestion.question);

        if(FiftyFifty.used) FF.setDisable(true);
        else FF.setText("Fifty Fifty");
        if(AskTheAudience.used) ATA.setDisable(true);
        else ATA.setText("Ask The Audience");

        if (Objects.equals(option, "Normal")) {
            A.setText(Questions.currentQuestion.toStringOption(0));
            B.setText(Questions.currentQuestion.toStringOption(1));
            C.setText(Questions.currentQuestion.toStringOption(2));
            D.setText(Questions.currentQuestion.toStringOption(3));
        } else if (Objects.equals(option, "FiftyFifty")) {
            FF.setDisable(true);
            FiftyFifty ff = new FiftyFifty();
            String[] remaining = ff.useLifeline();

            for (String r : remaining) {
                if (Objects.equals(r, "A")) A.setText(Questions.currentQuestion.toStringOption(0));
                else if (Objects.equals(r, "B")) B.setText(Questions.currentQuestion.toStringOption(1));
                else if (Objects.equals(r, "C")) C.setText(Questions.currentQuestion.toStringOption(2));
                else if (Objects.equals(r, "D")) D.setText(Questions.currentQuestion.toStringOption(3));
            }
        } else if (Objects.equals(option, "AskTheAudience")) {
            ATA.setDisable(true);
            AskTheAudience ata = new AskTheAudience();
            String[] percentage = ata.useLifeline();

            A.setText(Questions.currentQuestion.toStringOption(0) + " " + percentage[0]);
            B.setText(Questions.currentQuestion.toStringOption(1) + " " + percentage[1]);
            C.setText(Questions.currentQuestion.toStringOption(2) + " " + percentage[2]);
            D.setText(Questions.currentQuestion.toStringOption(3) + " " + percentage[3]);
        }

    }
    public void submitAnswer(ActionEvent event) throws IOException {
        try {
            String opt = ((RadioButton) answers.getSelectedToggle()).getId();

        MainApp.ans = switch (opt) {
            case ("A") -> "A";
            case ("B") -> "B";
            case ("C") -> "C";
            case ("D") -> "D";
            case ("FF") -> "FF";
            case ("ATA") -> "ATA";
            default -> "def";
        };


        if(MainApp.ans.equals("FF")) mainApp.fiftyFifty(event);
        else if(MainApp.ans.equals("ATA")) mainApp.askTheAudience(event);
        else {
            mainApp.wantToPlay(event);
        }
        } catch (NullPointerException e) {
            err.setText("Choose an option");
        }

    }
}