package com.example.justtestapplicationfx.model;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ParseHTMLPage {
    final private String page;
    private LinkedHashMap<String, String> axisDataArray;
    private LinkedHashMap<String, LinkedHashMap<String, String>> dataArrayWithCountry;

    public ParseHTMLPage(String page) {
        axisDataArray = new LinkedHashMap<>();
        this.page = page;

        try {
            Document anyPage;

            anyPage = getPage(page);

            System.out.println("Страница:" + page);
            Element statisticFromGraphic = anyPage.select("div[class=col-md-12]").first();
            Elements dates = statisticFromGraphic.select("script[type=text/javascript]");
            String datesString = dates.toString();


            //выделяем данные между 2 ключевыми словами
            String xAxis = datesString.substring(datesString.indexOf("categories:") + 14, datesString.indexOf("yAxis") - 22);
            String data = datesString.substring(datesString.indexOf("data:") + 7, datesString.indexOf("responsive") - 22);


            //создаем 2 массива из забранных со страницы данных
            String[] xAxisArray = xAxis.split("\",\"");
            String[] dataArray = data.split(",");

            for (int i = 0; i < xAxisArray.length; i++) {
                axisDataArray.put(xAxisArray[i], dataArray[i]);
            }

        } catch (NullPointerException | IOException ex) {
            ex.printStackTrace();
        }
        //System.out.println("Получаем вывод:\n" + axisDataArray);
    }

    private static Document getPage(String url) throws IOException {
        return Jsoup.parse(new URL(url), 5000);
    }

    public LinkedHashMap<String, String> returnDataArray() {
        return axisDataArray;
    }

}

