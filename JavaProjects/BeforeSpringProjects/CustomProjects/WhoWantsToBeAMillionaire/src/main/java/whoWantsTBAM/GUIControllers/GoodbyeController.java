package whoWantsTBAM.GUIControllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import whoWantsTBAM.MainApp;
import whoWantsTBAM.model.Player;
import whoWantsTBAM.service.WWTBAMService;
import javafx.scene.control.cell.*;


import java.util.List;

public class GoodbyeController implements Controller {

    @FXML
    private Label label1, label2, label3;
    @FXML TableView<Player> tableview;
    @FXML TableColumn<Player, Integer> column1, column3;
    @FXML TableColumn<Player, String> column2;
    //@FXML
    @Override
    public void setLayout(String s, String s1) {
        label1.setText("It was a nice game, " + MainApp.name);
        label2.setText(s);
        label3.setText(s1);

        column1.setCellValueFactory(new PropertyValueFactory<>("place"));
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        column3.setCellValueFactory(new PropertyValueFactory<>("resultMoney"));

        List<Player> topTen = WWTBAMService.getTopTen();
        topTen.forEach(p -> tableview.getItems().add(p));
    }

    public void submitAnswer(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}