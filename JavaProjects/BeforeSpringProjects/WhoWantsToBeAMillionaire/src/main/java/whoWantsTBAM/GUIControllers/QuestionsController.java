package whoWantsTBAM.GUIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import whoWantsTBAM.GameLogic.AskTheAudience;
import whoWantsTBAM.GameLogic.FiftyFifty;
import whoWantsTBAM.GameLogic.QuestionHandler;
import whoWantsTBAM.MainApp;

import java.io.IOException;
import java.util.Objects;

public class QuestionsController implements Controller {


    @FXML
    private ToggleGroup answers;
    @FXML
    public RadioButton A, B, C, D, ATA, FF;
    @FXML
    private Label question, err;
    @FXML
    public Button submit;

    MainApp mainApp = new MainApp();

    @Override
    public void setLayout(String option, String o2) {

        question.setText(QuestionHandler.currentQuestion.question);

        if(FiftyFifty.used) FF.setDisable(true);
        else FF.setText("Fifty Fifty");
        if(AskTheAudience.used) ATA.setDisable(true);
        else ATA.setText("Ask The Audience");

        if (Objects.equals(option, "Normal")) {
            A.setText(QuestionHandler.currentQuestion.toStringOption(0));
            B.setText(QuestionHandler.currentQuestion.toStringOption(1));
            C.setText(QuestionHandler.currentQuestion.toStringOption(2));
            D.setText(QuestionHandler.currentQuestion.toStringOption(3));
        }

        else if (Objects.equals(option, "FiftyFifty")) {
            FF.setDisable(true);
            FiftyFifty ff = new FiftyFifty();
            String[] remaining = ff.useLifeline();

            for (String r : remaining) {
                switch (r){
                    case "A" -> A.setText(QuestionHandler.currentQuestion.toStringOption(0));
                    case "B" -> B.setText(QuestionHandler.currentQuestion.toStringOption(1));
                    case "C" -> C.setText(QuestionHandler.currentQuestion.toStringOption(2));
                    case "D" -> D.setText(QuestionHandler.currentQuestion.toStringOption(3));
                }
            }
        }

        else if (Objects.equals(option, "AskTheAudience")) {
            ATA.setDisable(true);
            AskTheAudience ata = new AskTheAudience();
            String[] percentage = ata.useLifeline();

            A.setText(QuestionHandler.currentQuestion.toStringOption(0) + " " + percentage[0]);
            B.setText(QuestionHandler.currentQuestion.toStringOption(1) + " " + percentage[1]);
            C.setText(QuestionHandler.currentQuestion.toStringOption(2) + " " + percentage[2]);
            D.setText(QuestionHandler.currentQuestion.toStringOption(3) + " " + percentage[3]);
        }
    }
    public void submitAnswer(ActionEvent event) throws IOException {
        try {
            MainApp.ans = ((RadioButton) answers.getSelectedToggle()).getId();
            if(MainApp.ans.equals("FF")) mainApp.fiftyFifty(event);
            else if(MainApp.ans.equals("ATA")) mainApp.askTheAudience(event);
            else {mainApp.wantToPlay(event);}
        } catch (NullPointerException | InstantiationException | IllegalAccessException e) {
            err.setText("Choose an option");
        }
    }
}