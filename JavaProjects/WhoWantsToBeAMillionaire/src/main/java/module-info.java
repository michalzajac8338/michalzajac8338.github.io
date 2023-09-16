module com.example.creatingusername {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens whoWantsTBAM to javafx.fxml;
    exports whoWantsTBAM;

    exports whoWantsTBAM.GUIControllers;
    opens whoWantsTBAM.GUIControllers to javafx.fxml;

    exports whoWantsTBAM.GameLogic;
    opens whoWantsTBAM.GameLogic to javafx.fxml;

    exports whoWantsTBAM.service;
    opens whoWantsTBAM.service to javafx.fxml;

    exports whoWantsTBAM.model;
    opens whoWantsTBAM.model to javafx.base;

}