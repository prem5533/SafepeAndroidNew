package com.safepayu.wallet.ecommerce.model.request;

public class ReviewRequest {

    /**
     * order_id : 536
     * order_detail_id : 947
     * rating : 2
     * review : Awe Awe
     */

    private String order_id;
    private String order_detail_id;
    private String rating;
    private String review;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(String order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
