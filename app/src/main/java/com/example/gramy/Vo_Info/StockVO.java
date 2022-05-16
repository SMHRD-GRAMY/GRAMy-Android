package com.example.gramy.Vo_Info;

public class StockVO {

    private int stock_seq;
    private String stock_name;
    private double stock_weight;
    private String stock_date;
    private String stock_shelfLife;
    private int shelf_seq;
    private String stock_order;

    public int getStock_seq() {
        return stock_seq;
    }

    public void setStock_seq(int stock_seq) {
        this.stock_seq = stock_seq;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public double getStock_weight() {
        return stock_weight;
    }

    public void setStock_weight(double stock_weight) {
        this.stock_weight = stock_weight;
    }

    public String getStock_date() {
        return stock_date;
    }

    public void setStock_date(String stock_date) {
        this.stock_date = stock_date;
    }

    public String getStock_shelfLife() {
        return stock_shelfLife;
    }

    public void setStock_shelfLife(String stock_shelfLife) {
        this.stock_shelfLife = stock_shelfLife;
    }

    public int getShelf_seq() {
        return shelf_seq;
    }

    public void setShelf_seq(int shelf_seq) {
        this.shelf_seq = shelf_seq;
    }

    public String getStock_order() {
        return stock_order;
    }

    public void setStock_order(String stock_order) {
        this.stock_order = stock_order;
    }

    public StockVO(int stock_seq, String stock_name, double stock_weight, String stock_date, String stock_shelfLife, int shelf_seq, String stock_order) {
        this.stock_seq = stock_seq;
        this.stock_name = stock_name;
        this.stock_weight = stock_weight;
        this.stock_date = stock_date;
        this.stock_shelfLife = stock_shelfLife;
        this.shelf_seq = shelf_seq;
        this.stock_order = stock_order;
    }
}
