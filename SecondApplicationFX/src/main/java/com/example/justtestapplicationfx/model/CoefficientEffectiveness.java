package com.example.justtestapplicationfx.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


//класс, созданный для определения коэффициента борьбы против cow
//будем рассматривать каждые 3 месяца – 90 дней.
public class CoefficientEffectiveness {
    private LinkedHashMap<String, String> generalDataOnNumberCases;
    public static int allDays; //общее число дней, начиная с 1 заболевания
    private final int NUMBER_OF_DOTS = 60;
    private LinkedHashMap<Integer, Number> dayAndNumberOfCases;

    /**
     * @param dataArray массив данных, полученных с пропарсенной страницы
     *                  dayAndNumberOfCases в нем будем хранить наши коэффициенты
     */
    public CoefficientEffectiveness(LinkedHashMap<String, String> dataArray) {
        this.generalDataOnNumberCases = dataArray;
        dayAndNumberOfCases = new LinkedHashMap<>();

        //allDays = dataArray.size();

    }

    public LinkedHashMap<Integer, Number> calculateCoefficient() {
        int i = 0;
        System.out.println("Размер словаря: " + generalDataOnNumberCases.size());


        for (Map.Entry<String, String> entry : generalDataOnNumberCases.entrySet()) {
            i++;
            if (entry.getKey().equals("Jan 22, 2020")) {
                allDays = generalDataOnNumberCases.size();
                System.out.println("Общее количество дней = " + allDays);
            }
            //добавляем первую и последнюю точку
            if (i == 1) {
                dayAndNumberOfCases.put(i, Double.parseDouble(entry.getValue()) / i);
            }
            if (i == allDays) {
                dayAndNumberOfCases.put(i, Double.parseDouble(entry.getValue()) / i);
                break;
            }
            if (i % 40 == 0) { //значение счётчика достигает const
                //коэффициент в этот день
                dayAndNumberOfCases.put(i, Double.parseDouble(entry.getValue()) / i);
            }
        }


        return dayAndNumberOfCases;
    }

}
