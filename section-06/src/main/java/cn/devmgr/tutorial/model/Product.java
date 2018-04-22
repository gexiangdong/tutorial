package cn.devmgr.tutorial.model;

import java.util.Map;

public class Product {
    private String id;
    private String name;
    private int price;
    
    private Map<String, String> specs;
    private String[] images;

    
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
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    
    
    public Map<String, String> getSpecs() {
        return specs;
    }
    public void setSpecs(Map<String, String> specs) {
        this.specs = specs;
    }
    public String[] getImages() {
        return images;
    }
    public void setImages(String[] images) {
        this.images = images;
    }
}
