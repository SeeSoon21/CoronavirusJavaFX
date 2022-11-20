module com.example.justtestapplicationfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires java.logging;


    opens com.example.justtestapplicationfx to javafx.fxml;
    exports com.example.justtestapplicationfx;
    exports com.example.justtestapplicationfx.model;
    opens com.example.justtestapplicationfx.model to javafx.fxml;
    exports com.example.justtestapplicationfx.controller;
    opens com.example.justtestapplicationfx.controller to javafx.fxml;

}