package com.stdio.incomeandexpenseplanner;

public class DataModel {

    private String name;
    private String category;
    int id;
    private String cost;
    private String date;
    private String month;

    DataModel() {
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}