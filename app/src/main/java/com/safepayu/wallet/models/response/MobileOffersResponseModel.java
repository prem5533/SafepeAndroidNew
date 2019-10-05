package com.safepayu.wallet.models.response;

public class MobileOffersResponseModel {

    /**
     * category : SPL/RATE CUTTER
     * subCategory : Local
     * description : Talktime Rs. 55 - Local @ Rs. 0.012/sec - STD @ Rs. 0.012/sec - 200 MB 2G/3G/4G Data - Data Validity 28 day(s) - For 2G/3G/4G user
     * amount : 65
     * talktime : 55
     * validity : 28 Days
     * productId : 1
     * productName : Mobile
     * operatorId : 14
     * operatorName : Vodafone
     * circleId : 6
     * circleName : Delhi
     * planType : -1
     * metadata : {"stdpack_std_callrate":"0012","localpack_local_pulse":"1","limiteddatausage_freedata_data":"200","limiteddatausage_freedata_unit":"mb","limiteddatausage_freedata_datavalidity":"28","localpack_local_pulseunit":"sec","stdpack_std_pulseunit":"sec","stdpack_std_pulse":"1","talktime_normaltalktimers_amount":"55","localpack_local_callrate":"0012"}
     * id : null
     * primeStatus : null
     * isNewPlan : false
     * validityInDays : 28
     * isMostRechargedPlan : false
     * popular : true
     */

    private String category;
    private String subCategory;
    private String description;
    private int amount;
    private int talktime;
    private String validity;
    private int productId;
    private String productName;
    private int operatorId;
    private String operatorName;
    private int circleId;
    private String circleName;
    private int planType;
    private MetadataBean metadata;
    private Object id;
    private Object primeStatus;
    private boolean isNewPlan;
    private int validityInDays;
    private boolean isMostRechargedPlan;
    private boolean popular;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTalktime() {
        return talktime;
    }

    public void setTalktime(int talktime) {
        this.talktime = talktime;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public int getPlanType() {
        return planType;
    }

    public void setPlanType(int planType) {
        this.planType = planType;
    }

    public MetadataBean getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataBean metadata) {
        this.metadata = metadata;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getPrimeStatus() {
        return primeStatus;
    }

    public void setPrimeStatus(Object primeStatus) {
        this.primeStatus = primeStatus;
    }

    public boolean isIsNewPlan() {
        return isNewPlan;
    }

    public void setIsNewPlan(boolean isNewPlan) {
        this.isNewPlan = isNewPlan;
    }

    public int getValidityInDays() {
        return validityInDays;
    }

    public void setValidityInDays(int validityInDays) {
        this.validityInDays = validityInDays;
    }

    public boolean isIsMostRechargedPlan() {
        return isMostRechargedPlan;
    }

    public void setIsMostRechargedPlan(boolean isMostRechargedPlan) {
        this.isMostRechargedPlan = isMostRechargedPlan;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }

    public static class MetadataBean {
        /**
         * stdpack_std_callrate : 0012
         * localpack_local_pulse : 1
         * limiteddatausage_freedata_data : 200
         * limiteddatausage_freedata_unit : mb
         * limiteddatausage_freedata_datavalidity : 28
         * localpack_local_pulseunit : sec
         * stdpack_std_pulseunit : sec
         * stdpack_std_pulse : 1
         * talktime_normaltalktimers_amount : 55
         * localpack_local_callrate : 0012
         */

        private String stdpack_std_callrate;
        private String localpack_local_pulse;
        private String limiteddatausage_freedata_data;
        private String limiteddatausage_freedata_unit;
        private String limiteddatausage_freedata_datavalidity;
        private String localpack_local_pulseunit;
        private String stdpack_std_pulseunit;
        private String stdpack_std_pulse;
        private String talktime_normaltalktimers_amount;
        private String localpack_local_callrate;

        public String getStdpack_std_callrate() {
            return stdpack_std_callrate;
        }

        public void setStdpack_std_callrate(String stdpack_std_callrate) {
            this.stdpack_std_callrate = stdpack_std_callrate;
        }

        public String getLocalpack_local_pulse() {
            return localpack_local_pulse;
        }

        public void setLocalpack_local_pulse(String localpack_local_pulse) {
            this.localpack_local_pulse = localpack_local_pulse;
        }

        public String getLimiteddatausage_freedata_data() {
            return limiteddatausage_freedata_data;
        }

        public void setLimiteddatausage_freedata_data(String limiteddatausage_freedata_data) {
            this.limiteddatausage_freedata_data = limiteddatausage_freedata_data;
        }

        public String getLimiteddatausage_freedata_unit() {
            return limiteddatausage_freedata_unit;
        }

        public void setLimiteddatausage_freedata_unit(String limiteddatausage_freedata_unit) {
            this.limiteddatausage_freedata_unit = limiteddatausage_freedata_unit;
        }

        public String getLimiteddatausage_freedata_datavalidity() {
            return limiteddatausage_freedata_datavalidity;
        }

        public void setLimiteddatausage_freedata_datavalidity(String limiteddatausage_freedata_datavalidity) {
            this.limiteddatausage_freedata_datavalidity = limiteddatausage_freedata_datavalidity;
        }

        public String getLocalpack_local_pulseunit() {
            return localpack_local_pulseunit;
        }

        public void setLocalpack_local_pulseunit(String localpack_local_pulseunit) {
            this.localpack_local_pulseunit = localpack_local_pulseunit;
        }

        public String getStdpack_std_pulseunit() {
            return stdpack_std_pulseunit;
        }

        public void setStdpack_std_pulseunit(String stdpack_std_pulseunit) {
            this.stdpack_std_pulseunit = stdpack_std_pulseunit;
        }

        public String getStdpack_std_pulse() {
            return stdpack_std_pulse;
        }

        public void setStdpack_std_pulse(String stdpack_std_pulse) {
            this.stdpack_std_pulse = stdpack_std_pulse;
        }

        public String getTalktime_normaltalktimers_amount() {
            return talktime_normaltalktimers_amount;
        }

        public void setTalktime_normaltalktimers_amount(String talktime_normaltalktimers_amount) {
            this.talktime_normaltalktimers_amount = talktime_normaltalktimers_amount;
        }

        public String getLocalpack_local_callrate() {
            return localpack_local_callrate;
        }

        public void setLocalpack_local_callrate(String localpack_local_callrate) {
            this.localpack_local_callrate = localpack_local_callrate;
        }
    }
}
