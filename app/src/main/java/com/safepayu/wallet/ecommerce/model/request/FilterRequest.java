package com.safepayu.wallet.ecommerce.model.request;

import java.util.List;

public class FilterRequest {


    /**
     * brand_id : ["8"]
     * category_id : ["2","7"]
     * size : ["L"]
     * price : ["0","100"]
     * discount : ["0-10","11-34","35-49","50-100"]
     * search_key : Women
     * venue_id : 201911011148462
     */

    private String search_key;
    private String venue_id;
    private List<String> brand_id;
    private List<String> category_id;
    private List<String> size;
    private List<String> price;
    private List<String> discount;

    public String getSearch_key() {
        return search_key;
    }

    public void setSearch_key(String search_key) {
        this.search_key = search_key;
    }

    public String getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }

    public List<String> getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(List<String> brand_id) {
        this.brand_id = brand_id;
    }

    public List<String> getCategory_id() {
        return category_id;
    }

    public void setCategory_id(List<String> category_id) {
        this.category_id = category_id;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<String> getPrice() {
        return price;
    }

    public void setPrice(List<String> price) {
        this.price = price;
    }

    public List<String> getDiscount() {
        return discount;
    }

    public void setDiscount(List<String> discount) {
        this.discount = discount;
    }
}
