package com.prakriti.onlinefood.Pojo;

import java.io.Serializable;

/**
 * Created by Prakriti on 25/06/2017.
 */

public class FoodDetails implements Serializable {
    String id, name, price, details,image, material;

    public FoodDetails(){

    }

    public FoodDetails(String id, String name, String price, String details, String image, String material) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.details = details;
        this.image = image;
        this.material = material;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
