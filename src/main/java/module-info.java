module uabc.david.tareaarchivos {
    requires javafx.controls;
    requires javafx.fxml;


    opens uabc.david.tareaarchivos to javafx.fxml;
    exports uabc.david.tareaarchivos;
}