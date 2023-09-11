module com.example.creatingusername {
    requires javafx.controls;
    requires javafx.fxml;


    opens whoWantsTBAM to javafx.fxml;
    exports whoWantsTBAM;
    exports whoWantsTBAM.GUIControllers;
    opens whoWantsTBAM.GUIControllers to javafx.fxml;
    exports whoWantsTBAM.GameLogic;
    opens whoWantsTBAM.GameLogic to javafx.fxml;
}