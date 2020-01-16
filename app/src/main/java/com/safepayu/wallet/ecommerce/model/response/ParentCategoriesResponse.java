package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class ParentCategoriesResponse {

    /**
     * status : true
     * message : List Of Parent Category
     * categories : [{"id":7,"cat_name":"Beauty","parent_cat_id":0,"breadcrumb":"0","color":"64e69d","menu_level":1},{"id":19,"cat_name":"DSLR","parent_cat_id":0,"breadcrumb":"0","color":"965050","menu_level":1}]
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
