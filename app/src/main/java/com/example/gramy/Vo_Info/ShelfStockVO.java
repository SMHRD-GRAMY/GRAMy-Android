package com.example.gramy.Vo_Info;

public class ShelfStockVO {

    private int shelf_seq;
    private String shelf_name;
    private String user_id;
    private int stock_seq;
    private String stock_name;

    public ShelfStockVO(int shelf_seq, String shelf_name, String user_id, int stock_seq, String stock_name) {

        this.shelf_seq = shelf_seq;
        this.shelf_name = shelf_name;
        this.user_id = user_id;
        this.stock_seq = stock_seq;
        this.stock_name = stock_name;
    }

    public int getShelf_seq() {
        return shelf_seq;
    }

    public void setShelf_seq(int shelf_seq) {
        this.shelf_seq = shelf_seq;
    }

    public String getShelf_name() {
        return shelf_name;
    }

    public void setShelf_name(String shelf_name) {
        this.shelf_name = shelf_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

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

    @Override
    public String toString() {
        return "ShelfStockVO{" +
                "shelf_seq=" + shelf_seq +
                ", shelf_name='" + shelf_name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", stock_seq=" + stock_seq +
                ", stock_name='" + stock_name + '\'' +
                '}';
    }
}
