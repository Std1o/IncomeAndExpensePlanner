package com.stdio.incomeandexpenseplanner;

public class DataModel {
    String name;
    String category;
    int id;
    String date;
    String cost;
    String month;

    DataModel(String name, String category, int id, String date, String cost, String month) {
        this.name = name;
        this.category = category;
        this.id = id;
        this.date = date;
        this.cost = cost;
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }


    public String getDate() {
        return date;
    }

    public String getCost() {
        return cost;
    }

}