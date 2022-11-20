package com.example.justtestapplicationfx;

import com.example.justtestapplicationfx.controller.FXMLDocumentController;
import com.example.justtestapplicationfx.controller.ProgressDownloadOfSites;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * @author SeeSoon21(Егор Вилисов)
 */

//как критерий будем использовать коэффициент угла наклона касательной в каждой
//если взять масштаб x:y – 1 день:число заболевших, то знаменателем при нахождении тангенса угла всегда будет единица
// значит для нахождения коэффициента можно использовать отношение координат(абсциссы) следующей точки к текущей
public class Main extends Application {
    private final static String LOGFILE = "X:/programm/Java_Intelliji/JustTestApplicationFX/src/main/resources/logging.properties";
    public static Logger logger;
    static SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd ', время: ' h:mm:ss a '; '") ;

    //public static SwitchSceneController switchScene = new SwitchSceneController();

    //связь с лог-файлом
    static {
        try {
            FileInputStream ins = new FileInputStream(LOGFILE);
            LogManager.getLogManager().readConfiguration(ins);
            logger = Logger.getLogger(Main.class.getName());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        logger.log(Level.INFO, "\nВремя начала работы(изм. в наносекундах): " +
                formatForDateNow.format(new Date()).toString());

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("statistic-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root); //контейнер, содержащий все графические элементы внутри объекта Stage
        stage.setScene(scene);
        stage.show();

        //мб запустить 2-е окно в другом потоке???

    }

    public static void main(String[] args) {
        launch();
    }


}