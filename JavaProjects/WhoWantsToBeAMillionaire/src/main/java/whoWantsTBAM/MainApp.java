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
    Stage stage;
    Parent root;
    Scene scene;
    FXMLLoader loader;
    public static String css = MainApp.class.getResource("appStyle.css").toExternalForm();
    public static String name;
    public static String ans = null;
    boolean didOk = true;
    Questions q = new Questions();


    @Override
    public void start(Stage stage) throws IOException {

        Questions.createQuestionSet();

        q.askQuestion();

        loader = new FXMLLoader(getClass().getResource("setup.fxml"));
        root = loader.load();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.getIcons().add(new Image("C:\\Users\\micha\\Desktop\\JavaProjects\\WhoWantsToBeAMillionaire\\src\\main\\java\\whoWantsTBAM\\icon.png"));

        stage.setTitle("Who wants to be a millionaire?");
        stage.setScene(scene);
        stage.show();
    }

    public void wantToPlay(ActionEvent event) throws IOException {

        didOk = q.checkAnswer(ans);

        if(MoneyWon.index==12 || !didOk){
            loader = new FXMLLoader(getClass().getResource("goodbye.fxml"));
            root = loader.load();
            GoodbyeController controller = loader.getController();
            String s1 = MoneyWon.index==12 ? "CONGRATULATIONS" : "The proper answer was: "+currentQuestion.getProperABCD() + ": " + currentQuestion.getProperAnswer();
            controller.setLayout(MoneyWon.EndMoney(),s1);
        }

        else{
            loader = new FXMLLoader(getClass().getResource("continue.fxml"));
            root = loader.load();
            ContinueController controller = loader.getController();
            controller.setLayout();
        }

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void gameplay(ActionEvent event, String choice) throws IOException {

        if(Objects.equals(choice, "YES")){

            q.askQuestion();

            loader = new FXMLLoader(getClass().getResource("questions.fxml"));
            root = loader.load();

            QuestionsController controller = loader.getController();
            controller.setLayout("Normal");

        }
        else {
            loader = new FXMLLoader(getClass().getResource("goodbye.fxml"));
            root = loader.load();

            GoodbyeController controller = loader.getController();
            controller.setLayout(MoneyWon.resigned(), "Sometimes it's good to give up");
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void fiftyFifty(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("questions.fxml"));
        root = loader.load();

        QuestionsController controller = loader.getController();
        controller.setLayout("FiftyFifty");

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void askTheAudience(ActionEvent event) throws IOException {
        loader = new FXMLLoader(getClass().getResource("questions.fxml"));
        root = loader.load();

        QuestionsController controller = loader.getController();
        controller.setLayout("AskTheAudience");

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
