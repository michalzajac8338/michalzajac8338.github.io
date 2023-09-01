module com.example.creatingusername {
    requires javafx.controls;
    requires javafx.fxml;


    opens whoWantsTBAM to javafx.fxml;
    exports whoWantsTBAM;
}