package com.example.shoku;

public class Product {
    private String name;
    private String imgUrl;
    private int quantity;
    private double price;
    private String productId;


    public Product(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.quantity = 0;
    }

    public Product(String name, String imgUrl, double price, String productId) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.productId = productId;
        this.quantity = 0;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void plusQuantity() {
        this.quantity += 1;
    }

    public void minusQuantity() {
        if (this.quantity > 0) {
            this.quantity -= 1;
        }
    }
}
