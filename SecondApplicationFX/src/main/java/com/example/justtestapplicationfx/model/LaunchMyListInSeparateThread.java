package com.example.justtestapplicationfx.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Класс, который создан для запуска потока для некоторого количества страниц
 * listParsedPages – лист пропарсенных страниц
 */
public class LaunchMyListInSeparateThread implements Runnable{
    private final ArrayList<String> listOfSites; //получаемый список сайтов
    private final ArrayList<ParseHTMLPage> listParsedPages; //возвращаемое число пропасенных страниц
    private final ArrayList<String> countryList;
    private final LinkedList<LinkedHashMap<Integer, Number>> listCoefficientForCountry;

    private LinkedHashMap<Integer, String> listOfSitesHashMap; //хэшмэп для разделения сайтов при условии
    private int sizeListOfSites;

    /**
     * @param listOfSites лист сайтов, которые нужно пропарсить
     */
    public LaunchMyListInSeparateThread(ArrayList<String> listOfSites) {
        this.listOfSites = listOfSites;
        listParsedPages = new ArrayList<>();
        countryList = new ArrayList<>();
        listCoefficientForCountry = new LinkedList<>();
        sizeListOfSites = listOfSites.size();
    }

    public void addNewArraySites(ArrayList<String> my) {

    }

    @Override
    // парсим каждый сайт из переданного списка
    public void run() {
        for (int i = 0; i < listOfSites.size(); i++) {
            System.out.println("Вывод для сайта: " + listOfSites.get(i) + '\n');
            ParseHTMLPage tempParsedHTML = new ParseHTMLPage(listOfSites.get(i)); //получаем объект с нужными данными

            //добавляем на график название каждой из стран
            listParsedPages.add(tempParsedHTML);
            countryList.add(listOfSites.get(i).substring(listOfSites.get(i).indexOf("country/") + 8,
                    listOfSites.get(i).indexOf("/#graph")));
            System.out.println("Название страны:" + countryList.get(i));

            //получаем наши коэффициенты
            CoefficientEffectiveness coefficientForCountry = new CoefficientEffectiveness(tempParsedHTML.returnDataArray());
            listCoefficientForCountry.add(coefficientForCountry.calculateCoefficient());

        }

    }

    //лист пропарсенных страниц
    public ArrayList<ParseHTMLPage> returnParsedPages(){
        return listParsedPages;
    }

    //лист названий стран
    public ArrayList<String> getCountryList() {
        return countryList;
    }

    //список, в котором содержится список коэффициентов для каждой страны
    public LinkedList<LinkedHashMap<Integer, Number>> getListCoefficientForCountry() {
        return listCoefficientForCountry;
    }
}
