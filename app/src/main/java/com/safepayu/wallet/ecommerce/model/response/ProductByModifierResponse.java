package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class ProductByModifierResponse {

    /**
     * status : true
     * message : Product modifiers Details
     * products : {"id":116,"product_id":20,"modifier_name":"15","selling_price":"0.00","avl_quantity":200,"modifier_images":[]}
     * loyalitypoints : {"loyalty_points_value":0.25,"loyalty_points":0,"total_loyalty_points_value":0}
     */

    private boolean status;
    private String message;
    private ProductsBean products;
    private LoyalitypointsBean loyalitypoints;

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

    public ProductsBean getProducts() {
        return products;
    }

    public void setProducts(ProductsBean products) {
        this.products = products;
    }

    public LoyalitypointsBean getLoyalitypoints() {
        return loyalitypoints;
    }

    public void setLoyalitypoints(LoyalitypointsBean loyalitypoints) {
        this.loyalitypoints = loyalitypoints;
    }

    public static class ProductsBean {
        /**
         * id : 116
         * product_id : 20
         * modifier_name : 15
         * selling_price : 0.00
         * avl_quantity : 200
         * modifier_images : []
         */

        private int id;
        private int product_id;
        private String modifier_name;
        private String selling_price;
        private int avl_quantity;
        private List<?> modifier_images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getModifier_name() {
            return modifier_name;
        }

        public void setModifier_name(String modifier_name) {
            this.modifier_name = modifier_name;
        }

        public String getSelling_price() {
            return selling_price;
        }

        public void setSelling_price(String selling_price) {
            this.selling_price = selling_price;
        }

        public int getAvl_quantity() {
            return avl_quantity;
        }

        public void setAvl_quantity(int avl_quantity) {
            this.avl_quantity = avl_quantity;
        }

        public List<?> getModifier_images() {
            return modifier_images;
        }

        public void setModifier_images(List<?> modifier_images) {
            this.modifier_images = modifier_images;
        }
    }

    public static class LoyalitypointsBean {
        /**
         * loyalty_points_value : 0.25
         * loyalty_points : 0
         * total_loyalty_points_value : 0
         */

        private double loyalty_points_value;
        private int loyalty_points;
        private int total_loyalty_points_value;

        public double getLoyalty_points_value() {
            return loyalty_points_value;
        }

        public void setLoyalty_points_value(double loyalty_points_value) {
            this.loyalty_points_value = loyalty_points_value;
        }

        public int getLoyalty_points() {
            return loyalty_points;
        }

        public void setLoyalty_points(int loyalty_points) {
            this.loyalty_points = loyalty_points;
        }

        public int getTotal_loyalty_points_value() {
            return total_loyalty_points_value;
        }

        public void setTotal_loyalty_points_value(int total_loyalty_points_value) {
            this.total_loyalty_points_value = total_loyalty_points_value;
        }
    }
}
