package com.example.justtestapplicationfx.controller;

import com.example.justtestapplicationfx.Main;
import com.example.justtestapplicationfx.model.LaunchMyListInSeparateThread;
import com.example.justtestapplicationfx.model.ParseHTMLPage;
import com.example.justtestapplicationfx.model.Sites;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;

/**
 * listParsedPages лист пропарсенных страниц
 * listObservableData лист с данными для сцены(for building general cases)
 * listCoefficientObservableData лист с данными для сцены(for building coefficient)
 * listCoefficientForCountry лист, содержащий списки коэффициентов для каждой страны
 * myList объект класс которого расширяет интерфейс Runnable. Предназначен для создания нового потока.
 * size размер списка с сайтами
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private MenuButton setParametersButton;

    @FXML
    private LineChart<String, Number> lineChart;
    private ArrayList<ParseHTMLPage> listParsedPages;
    private LinkedList<ObservableList<XYChart.Data<String, Number>>> listObservableData;

    private LinkedList<LinkedHashMap<Integer, Number>> listCoefficientForCountry;

    private LaunchMyListInSeparateThread myList; //
    private static int sizeListSites;

    public static int returnSizeListOfSites() {
        return sizeListSites;
    }

    //измерение времени
    static SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd ', время: ' h:mm:ss a '; '") ;

    private final String[] myStringArray = {
            "https://www.worldometers.info/coronavirus/country/china/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/russia/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/italy/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/france/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/spain/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/germany/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/brazil/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/turkey/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/argentina/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/iran/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/colombia/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/poland/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/indonesia/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/argentina/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/iran/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/mexico/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/ukraine/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/south-africa/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/netherlands/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/philippines/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/malaysia/#graph-deaths-daily",
            "https://www.worldometers.info/coronavirus/country/canada/#graph-deaths-daily",
    };


    //"наблюдаемый лист" – массив для отслеживания изменений? XYChart – родительский класс для 2 осей координат


    @FXML
    public void btn(ActionEvent event) {
        buildLineChart();
    }

    @FXML  //строительство графика коэффициентов
    public void btn2(ActionEvent event) {

        listObservableData = new LinkedList<>();

        lineChart.getData().clear(); //очищаем полотно

        //строим график
        int i = 0;
        Main.logger.log(Level.INFO, "\nВремя начала работы строительства графика(coefficient)\n" +
                formatForDateNow.format(new Date()).toString());
        for (LinkedHashMap<Integer, Number> temp: listCoefficientForCountry) {
            System.out.println(temp);
            listObservableData.add(FXCollections.observableArrayList());
            for (Map.Entry<Integer, Number> entry: temp.entrySet()) {
                listObservableData.get(i).add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
            }
            XYChart.Series<String, Number> tempSeries = new XYChart.Series<>(); //создаем серию, в которую сложим данные
            tempSeries.setData(listObservableData.get(i)); //устанавливаем данные в серию
            tempSeries.setName(myList.getCountryList().get(i));//присваиваем каждой серии(графику функции) название соответствующей страны
            lineChart.getData().add(tempSeries); //и поместим на график
            i++;
        }
        Main.logger.log(Level.INFO, "Время окончания строительства графика(coefficient)\n" +
                formatForDateNow.format(new Date()).toString());

    }

    //функция для строительства графиков по данным
    public void buildLineChart() {
        listObservableData = new LinkedList<>();

        //блок для строительства графика
        lineChart.getData().clear();

        Main.logger.log(Level.INFO, "\nВремя начала работы строительства графика(all cases)" +
                formatForDateNow.format(new Date()).toString());

        for (int i = 0; i < sizeListSites; i++) {
            //добавляем в лист данных объекты для содержания принимаемых данных
            listObservableData.add(FXCollections.observableArrayList()); //в самой функции зашито создание объекта
            //проходим по возвращаемым элементам типа LinkedHashMap, которые содержат данные одной страницы
            for (Map.Entry<String, String> entry : listParsedPages.get(i).returnDataArray().entrySet()) {
                listObservableData.get(i).add((new XYChart.Data<>(entry.getKey(), Integer.parseInt(entry.getValue()))));
            }
            XYChart.Series<String, Number> tempSeries = new XYChart.Series<>(); //создаем серию, в которую сложим данные
            tempSeries.setData(listObservableData.get(i)); //устанавливаем данные в серию
            tempSeries.setName(myList.getCountryList().get(i));//присваиваем каждой серии(графику функции) название соответствующей страны
            lineChart.getData().add(tempSeries); //и поместим на график
        }

        Main.logger.log(Level.INFO, "Время окончания строительства графика(all cases)\n" +
                formatForDateNow.format(new Date()).toString());
    }

    //действия при выборе одной из копок MenuItem
    public void chooseParameter() {
        MenuItem build_three_countries = new MenuItem("Build the first 3 countries");
        MenuItem build_one_country = new MenuItem("Build one country");
        MenuItem build_all_countries = new MenuItem("Build all countries");

        setParametersButton.getItems().addAll(build_three_countries, build_one_country, build_all_countries); //добавляем листья

        build_three_countries.setOnAction((e)->{
            System.out.println("Выбрано строительство 3 стран");
            setParametersButton.setText(build_three_countries.getText());
            buildCountries(new Sites(
                    "https://www.worldometers.info/coronavirus/country/china/#graph-deaths-daily",
                    "https://www.worldometers.info/coronavirus/country/russia/#graph-deaths-daily",
                    "https://www.worldometers.info/coronavirus/country/italy/#graph-deaths-daily"
            ));
            buildLineChart();
        });

        build_one_country.setOnAction((e) -> {
            System.out.println("Выбрано строительство 1 страны");
            setParametersButton.setText(build_one_country.getText());
            buildCountries(new Sites(
                    "https://www.worldometers.info/coronavirus/country/china/#graph-deaths-daily"
            ));
            buildLineChart();
        });

        build_all_countries.setOnAction((e) -> {
            System.out.println("Выбрано строительство всех стран");
            setParametersButton.setText(build_all_countries.getText());
            buildCountries(new Sites(myStringArray));
            buildLineChart();
        });


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO

        Main.logger.log(Level.INFO, "\nНачало инициализации" +
                formatForDateNow.format(new Date()).toString());


        Sites sites = new Sites(myStringArray); //объект, для работы с текстовым видом списка сайтов
        buildCountries(sites);

        //работа с копкой splitMenuItem
        chooseParameter();
    }

    public void buildCountries(Sites sites) {

        listParsedPages = new ArrayList<>();
        listCoefficientForCountry = new LinkedList<>();
        myList = new LaunchMyListInSeparateThread(sites.getListOfSites());

        myList.run();
        listParsedPages = myList.returnParsedPages();
        listCoefficientForCountry = myList.getListCoefficientForCountry();
        sizeListSites = sites.getListOfSites().size();

        Main.logger.log(Level.INFO, "\nОкончание инициализации" +
                formatForDateNow.format(new Date()).toString());

        System.out.println("\nДанные с сайта");
        for (int i = 0; i < sizeListSites; i++) {
            System.out.println(listParsedPages.get(i).returnDataArray());
        }

        /*LinkedHashMap<Integer, String> tempHashMap = sites.conditionHashMap();
        for (Map.Entry<Integer, String> temp: tempHashMap.entrySet()) {
            System.out.println(temp.getKey() + ": " + temp.getValue());
        }*/
    }
}
