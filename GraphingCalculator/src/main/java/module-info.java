module org.example.graphingcalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.graphingcalculator to javafx.fxml;
    exports org.example.graphingcalculator;
}