package com.safepayu.wallet.models.response;

import java.util.List;

public class ServiceChargeResponse {

    /**
     * status : true
     * message : List of taxes.
     * tax : [{"id":1,"tax_name":"GST","tax_value":"18.00"}]
     */

    private boolean status;
    private String message;
    private List<TaxBean> tax;

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

    public List<TaxBean> getTax() {
        return tax;
    }

    public void setTax(List<TaxBean> tax) {
        this.tax = tax;
    }

    public static class TaxBean {
        /**
         * id : 1
         * tax_name : GST
         * tax_value : 18.00
         */

        private int id;
        private String tax_name;
        private String tax_value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTax_name() {
            return tax_name;
        }

        public void setTax_name(String tax_name) {
            this.tax_name = tax_name;
        }

        public String getTax_value() {
            return tax_value;
        }

        public void setTax_value(String tax_value) {
            this.tax_value = tax_value;
        }
    }
}
