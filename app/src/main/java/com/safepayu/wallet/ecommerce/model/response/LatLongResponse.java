package com.safepayu.wallet.ecommerce.model.response;

public class LatLongResponse {

    /**
     * status : true
     * message : latitude and longitude found
     * data : {"longt":"77.23594","prov":"IN","city":"Delhi","country":"India","postal":"100006","latt":"28.65673"}
     */

    private boolean status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * longt : 77.23594
         * prov : IN
         * city : Delhi
         * country : India
         * postal : 100006
         * latt : 28.65673
         */

        private String longt;
        private String prov;
        private String city;
        private String country;
        private String postal;
        private String latt;

        public String getLongt() {
            return longt;
        }

        public void setLongt(String longt) {
            this.longt = longt;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPostal() {
            return postal;
        }

        public void setPostal(String postal) {
            this.postal = postal;
        }

        public String getLatt() {
            return latt;
        }

        public void setLatt(String latt) {
            this.latt = latt;
        }
    }
}
