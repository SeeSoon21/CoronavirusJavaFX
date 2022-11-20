package com.example.justtestapplicationfx.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * класс, созданный для получения адресов страниц с информацией о какой-либо стране
 */
public class Sites {
    private ArrayList<String> listOfSites;
    private LinkedHashMap<Integer, String> linkedHashMapOfSites;
    int sizeListOfSites;

    //множество сайтов
    public Sites(String... sites) {
        listOfSites = new ArrayList<>();
        Collections.addAll(listOfSites, sites);

        sizeListOfSites = listOfSites.size();
    }

    public ArrayList<String> getListOfSites() {
        return listOfSites;
    }

    public LinkedHashMap<Integer, String> conditionHashMap() {
        linkedHashMapOfSites = new LinkedHashMap<>();
        int i = 0;
        int j = 0;
        StringBuilder tempString = new StringBuilder();

        //делим сайты только в случае, если число сайтов > 10
        if (sizeListOfSites > 10) {
            for (String site: listOfSites) {
                if (i % 7 == 0) { //если текущая длина перебираемого листа кратна 7
                    linkedHashMapOfSites.put(j, tempString.toString());
                    j++; //новый ключ – собраны 7 сайтов
                    tempString = new StringBuilder();
                }
                tempString.append(site).append('\n');
                i++;
            }
            if (sizeListOfSites - (j*7) != 0) {
                linkedHashMapOfSites.put(j - 1, linkedHashMapOfSites.get(j - 1) + tempString);
            }
        }

        return linkedHashMapOfSites;
    }

    public void setListOfSites(ArrayList<String> listOfSites) {
        this.listOfSites = listOfSites;
    }

    //добавления одного сайта
    public void addNewSite(String url) {
        listOfSites.add(url);
    }
}
