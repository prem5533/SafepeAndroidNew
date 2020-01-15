package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class CategoriesResponse {

    /**
     * status : true
     * message : List Of Parent Category
     * categories : [{"id":32,"cat_name":"Ayurveda","parent_cat_id":7,"breadcrumb":"7","color":"64e6a9","menu_level":2},{"id":20,"cat_name":"Baby and Toddler","parent_cat_id":10,"breadcrumb":"10","color":"440aee","menu_level":2}]
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
         * id : 32
         * cat_name : Ayurveda
         * parent_cat_id : 7
         * breadcrumb : 7
         * color : 64e6a9
         * menu_level : 2
         */

        private int id;
        private String cat_name;
        private int parent_cat_id;
        private String breadcrumb;
        private String color;
        private int menu_level;

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
    }
}
