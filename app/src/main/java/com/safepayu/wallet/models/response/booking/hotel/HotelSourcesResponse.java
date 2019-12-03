package com.safepayu.wallet.models.response.booking.hotel;

import java.util.List;

public class HotelSourcesResponse {

    /**
     * status : true
     * mesage : Data retrieved successfully
     * data : [{"CityId":1,"CityKeyId":0,"CityName":"AGRA","LocationId":0,"LocationName":null,"StateCode":null,"StateName":null,"CountryCode":null,"CountryName":null,"IsAlias":false,"StateId":0,"CreatedBy":null,"CreatedDate":"0001-01-01T00:00:00","ModifiedBy":null,"ModifiedDate":"0001-01-01T00:00:00"}]
     */

    private boolean status;
    private String mesage;
    private List<DataBean> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * CityId : 1
         * CityKeyId : 0
         * CityName : AGRA
         * LocationId : 0
         * LocationName : null
         * StateCode : null
         * StateName : null
         * CountryCode : null
         * CountryName : null
         * IsAlias : false
         * StateId : 0
         * CreatedBy : null
         * CreatedDate : 0001-01-01T00:00:00
         * ModifiedBy : null
         * ModifiedDate : 0001-01-01T00:00:00
         */

        private int CityId;
        private int CityKeyId;
        private String CityName;
        private int LocationId;
        private Object LocationName;
        private Object StateCode;
        private Object StateName;
        private Object CountryCode;
        private Object CountryName;
        private boolean IsAlias;
        private int StateId;
        private Object CreatedBy;
        private String CreatedDate;
        private Object ModifiedBy;
        private String ModifiedDate;

        public int getCityId() {
            return CityId;
        }

        public void setCityId(int CityId) {
            this.CityId = CityId;
        }

        public int getCityKeyId() {
            return CityKeyId;
        }

        public void setCityKeyId(int CityKeyId) {
            this.CityKeyId = CityKeyId;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }

        public int getLocationId() {
            return LocationId;
        }

        public void setLocationId(int LocationId) {
            this.LocationId = LocationId;
        }

        public Object getLocationName() {
            return LocationName;
        }

        public void setLocationName(Object LocationName) {
            this.LocationName = LocationName;
        }

        public Object getStateCode() {
            return StateCode;
        }

        public void setStateCode(Object StateCode) {
            this.StateCode = StateCode;
        }

        public Object getStateName() {
            return StateName;
        }

        public void setStateName(Object StateName) {
            this.StateName = StateName;
        }

        public Object getCountryCode() {
            return CountryCode;
        }

        public void setCountryCode(Object CountryCode) {
            this.CountryCode = CountryCode;
        }

        public Object getCountryName() {
            return CountryName;
        }

        public void setCountryName(Object CountryName) {
            this.CountryName = CountryName;
        }

        public boolean isIsAlias() {
            return IsAlias;
        }

        public void setIsAlias(boolean IsAlias) {
            this.IsAlias = IsAlias;
        }

        public int getStateId() {
            return StateId;
        }

        public void setStateId(int StateId) {
            this.StateId = StateId;
        }

        public Object getCreatedBy() {
            return CreatedBy;
        }

        public void setCreatedBy(Object CreatedBy) {
            this.CreatedBy = CreatedBy;
        }

        public String getCreatedDate() {
            return CreatedDate;
        }

        public void setCreatedDate(String CreatedDate) {
            this.CreatedDate = CreatedDate;
        }

        public Object getModifiedBy() {
            return ModifiedBy;
        }

        public void setModifiedBy(Object ModifiedBy) {
            this.ModifiedBy = ModifiedBy;
        }

        public String getModifiedDate() {
            return ModifiedDate;
        }

        public void setModifiedDate(String ModifiedDate) {
            this.ModifiedDate = ModifiedDate;
        }
    }
}
