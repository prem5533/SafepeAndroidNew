package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class ParentCategoryResponse {

    /**
     * status : true
     * message : List Of Parent Category
     * categories : [{"id":7,"cat_name":"Beauty","parent_cat_id":0,"breadcrumb":"0","color":"64e69d","menu_level":1,"image":"beauty.jpg"},{"id":19,"cat_name":"DSLR","parent_cat_id":0,"breadcrumb":"0","color":"965050","menu_level":1,"image":"category-default-upload.jpg"},{"id":3,"cat_name":"Electronics","parent_cat_id":0,"breadcrumb":"0","color":"64e69d","menu_level":1,"image":"category-default-upload.jpg"},{"id":2,"cat_name":"Fashion","parent_cat_id":0,"breadcrumb":"0","color":"cf64e6","menu_level":1,"image":"fashion.jpg"},{"id":24,"cat_name":"Flowers","parent_cat_id":0,"breadcrumb":"0","color":"2e7061","menu_level":1,"image":"category-default-upload.jpg"},{"id":8,"cat_name":"Furniture","parent_cat_id":0,"breadcrumb":"0","color":"64e6a1","menu_level":1,"image":"category-default-upload.jpg"},{"id":10,"cat_name":"Grocery","parent_cat_id":0,"breadcrumb":"0","color":"3713a0","menu_level":1,"image":"category-default-upload.jpg"},{"id":12,"cat_name":"Home & Kitchen","parent_cat_id":0,"breadcrumb":"0","color":"b864e6","menu_level":1,"image":"category-default-upload.jpg"},{"id":14,"cat_name":"iPads","parent_cat_id":0,"breadcrumb":"0","color":"64b5e6","menu_level":1,"image":"category-default-upload.jpg"},{"id":9,"cat_name":"Jewellery","parent_cat_id":0,"breadcrumb":"0","color":"64a1e6","menu_level":1,"image":"category-default-upload.jpg"},{"id":18,"cat_name":"Laptop Accessories","parent_cat_id":0,"breadcrumb":"0","color":"e49c9c","menu_level":1,"image":"category-default-upload.jpg"},{"id":11,"cat_name":"Luggage & Bags","parent_cat_id":0,"breadcrumb":"0","color":"64e69a","menu_level":1,"image":"category-default-upload.jpg"},{"id":15,"cat_name":"Men Grooming","parent_cat_id":0,"breadcrumb":"0","color":"64cde6","menu_level":1,"image":"category-default-upload.jpg"},{"id":13,"cat_name":"Mobile Accessories","parent_cat_id":0,"breadcrumb":"0","color":"64e69d","menu_level":1,"image":"category-default-upload.jpg"},{"id":16,"cat_name":"Musical Instrument","parent_cat_id":0,"breadcrumb":"0","color":"e6d264","menu_level":1,"image":"category-default-upload.jpg"},{"id":1,"cat_name":"Sports","parent_cat_id":0,"breadcrumb":"0","color":"64e6e0","menu_level":1,"image":"sports.jpg"},{"id":17,"cat_name":"Video Games","parent_cat_id":0,"breadcrumb":"0","color":"275d7a","menu_level":1,"image":"category-default-upload.jpg"}]
     */

    private boolean status;
    private String message;
    private List<CategoriesBean> categories;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public static class CategoriesBean {
        /**
         * id : 7
         * cat_name : Beauty
         * parent_cat_id : 0
         * breadcrumb : 0
         * color : 64e69d
         * menu_level : 1
         * image : beauty.jpg
         */

        private int id;
        private String cat_name;
        private int parent_cat_id;
        private String breadcrumb;
        private String color;
        private int menu_level;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public int getParent_cat_id() {
            return parent_cat_id;
        }

        public void setParent_cat_id(int parent_cat_id) {
            this.parent_cat_id = parent_cat_id;
        }

        public String getBreadcrumb() {
            return breadcrumb;
        }

        public void setBreadcrumb(String breadcrumb) {
            this.breadcrumb = breadcrumb;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getMenu_level() {
            return menu_level;
        }

        public void setMenu_level(int menu_level) {
            this.menu_level = menu_level;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
