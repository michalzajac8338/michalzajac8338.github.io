package whoWantsTBAM;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static whoWantsTBAM.Questions.currentQuestion;

public class MainApp extends Application {
    Parent root;
    FXMLLoader loader;
    Scene scene;
    Stage stage;
    public static final String css = Objects.requireNonNull(MainApp.class.getResource("appStyle.css")).toExternalForm();
    public static String name;
    public static String ans = null;
    boolean didOk = true;
    Questions q = new Questions();


    @Override
    public void start(Stage stage) throws IOException {

        Questions.createQuestionSet();

        q.askQuestion();
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("icon.png"))));

        loader = new FXMLLoader(getClass().getResource("setup.fxml"));
        root = loader.load();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setTitle("Who wants to be a millionaire?");
        stage.setScene(scene);
        stage.show();
    }

    public void wantToPlay(ActionEvent event) throws IOException, InstantiationException, IllegalAccessException {

        didOk = q.checkAnswer(ans);

        if(MoneyWon.index==12 || !didOk){
            String s1 = MoneyWon.index==12 ? "CONGRATULATIONS" : "The proper answer was: "+currentQuestion.getProperABCD() + ": " + currentQuestion.getProperAnswer();
            displayNextScene("goodbye.fxml", MoneyWon.EndMoney(),s1,event);
        }

        else{
            displayNextScene("continue.fxml", "", "",event);
        }
    }

    public void gameplay(ActionEvent event, String choice) throws IOException {

        if(Objects.equals(choice, "YES")){
            q.askQuestion();
            displayNextScene("questions.fxml", "Normal", "", event);
        }
        else {
            displayNextScene("goodbye.fxml", MoneyWon.resigned(), "Sometimes it's good to give up",event);
        }
    }

    public void fiftyFifty(ActionEvent event) throws IOException {
        displayNextScene("questions.fxml", "FiftyFifty", "", event);
    }
    public void askTheAudience(ActionEvent event) throws IOException, InstantiationException, IllegalAccessException {
        displayNextScene("questions.fxml", "AskTheAudience", "", event);
    }
    public void displayNextScene(String fxmlName, String opt, String opt2, ActionEvent event) throws IOException{
        loader = new FXMLLoader(getClass().getResource(fxmlName));
        root = loader.load();

        Controller controller = loader.getController();
        controller.setLayout(opt, opt2);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public static void main (String[]args){
        launch();
    }
}
