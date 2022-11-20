package com.example.justtestapplicationfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

//класс, созданный для отображения полосы загрузки
public class ProgressDownloadOfSites implements Initializable {
    @FXML
    private ProgressBar numberOfDownloaded;
    @FXML
    private Label myLabel;
    private int sizeListOfSites; //размерность листа с сайтами
    private double progress = 0.0;


    public ProgressDownloadOfSites(int sizeListOfSites) {
        this.sizeListOfSites = sizeListOfSites;
    }

    public void loadingBar(int currentValue) {
        if (currentValue < sizeListOfSites) {
            progress++;
            numberOfDownloaded.setProgress(progress/sizeListOfSites);
            myLabel.setText(progress/sizeListOfSites * 100 + "%");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberOfDownloaded.setStyle("-fx-accent: red;");
    }
}
