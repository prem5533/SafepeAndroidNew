package com.safepayu.wallet.models.response;

import java.util.List;

public class RechargePlanResponse {

    /**
     * status : true
     * message : Plan find Successfully...
     * data : {"SPL":[{"amount":"12","detail":"Get 120 Local/National SMS for 10 days","validity":"10 Days","talktime":"NA"},{"amount":"16","detail":"Rs16: Get 1 GB 4G/3G/2G Data; Validity: 1 Day","validity":"1","talktime":"NA"},{"amount":"19","detail":"Get Unlimited Voice Calls + 150 MB Data + 100 SMS for 2 Days","validity":"2 Days","talktime":"NA"},{"amount":"24","detail":"Call rate of 2.5p/sec  100 on-net night minutes for 14 days","validity":"14 Days","talktime":"NA"},{"amount":"26","detail":"Get 250 local/national SMS for 28 days","validity":"28 Days","talktime":"NA"},{"amount":"36","detail":"Get 350 local/national SMS for 28 days","validity":"28 Days","talktime":"NA"},{"amount":"49","detail":"Rs49: Rs38 Limited Validity Talktime+Local/National Calls@2.5p/sec + 100MB; Validity: 28 Days","validity":"28","talktime":"38"},{"amount":"79","detail":"Rs79: Rs64 Limited Validity Talktime+Local/National Calls@1p/sec + 200MB; Validity: 28 Days","validity":"28","talktime":"64"},{"amount":"149","detail":"Rs149: Now get Truly Unlimited Local/National Calls to all Networks+2GB Data+300SMS.Validity:28 Days","validity":"28","talktime":"NA"},{"amount":"219","detail":"Now get Truly Unlimited Local/National Calls to all Networks+1GB/Day+100SMS/Day.Validity:28Days","validity":"28","talktime":"NA"},{"amount":"249","detail":"Now get Truly Unlimited Local/National Calls to all Networks+1.5GB/Day+100SMS/Day. Validity:28 Days","validity":"28 Days","talktime":"NA"},{"amount":"299","detail":"Now get Truly Unlimited Local/National Calls to all Networks +2GB/Day Data+100SMS/Day. Validity: 28 Days","validity":"28 Days","talktime":"NA"},{"amount":"379","detail":"Now get Truly Unlimited Local/National Calls to all Networks+6GB Data+1000SMS. Validity: 84 Days","validity":"84 Days","talktime":"NA"},{"amount":"399","detail":"Now get Truly Unlimited Local/National Calls to all Networks+1.5GB/Day+100SMS/Day. Validity:56 Days","validity":"56","talktime":"NA"},{"amount":"449","detail":"Now get Truly Unlimited Local/National Calls to all Networks+2GB/Day+100SMS/Day. Validity:56 Days","validity":"56 Days","talktime":"NA"},{"amount":"599","detail":"Rs599:Now get Truly Unlimited Local/National Calls to all Networks+1.5GB/Day+100SMS/Day.Validity:84Days","validity":"84","talktime":"NA"},{"amount":"699","detail":"Now get Truly Unlimited Local/National Calls to all Networks +2GB/Day Data+100SMS/Day. Validity: 84 Days","validity":"84 Days","talktime":"NA"},{"amount":"1499","detail":"Now get Truly Unlimited Local/National Calls to all Networks +24GB Data+3600SMS. Validity: 365 Days","validity":"365 Days","talktime":"NA"},{"amount":"2399","detail":"Now get Truly Unlimited Local/National Calls to all Networks +1.5GB/Day Data+100SMS/Day. Validity: 365 Days","validity":"365 Days","talktime":"NA"}],"DATA":[{"amount":"16","detail":"Rs16: Get 1 GB 4G/3G/2G Data; Validity: 1 Day","validity":"1","talktime":"NA"},{"amount":"48","detail":"Rs48: Get 3 GB 4G/3G/2G Data; Validity: 28 Days","validity":"28","talktime":"NA"},{"amount":"49","detail":"Rs49: Rs38 Limited Validity Talktime+Local/National Calls@2.5p/sec + 100MB; Validity: 28 Days","validity":"28","talktime":"38"},{"amount":"98","detail":"Rs98: Get 6 GB 4G/3G/2G Data; Validity: 28 Days","validity":"28","talktime":"NA"}],"FTT":[{"amount":"10","detail":"Regular Talktime","validity":"NA","talktime":"7.47"}],"TUP":[{"amount":"10","detail":"Regular Talktime","validity":"NA","talktime":"7.47"},{"amount":"20","detail":"Regular Talktime","validity":"NA","talktime":"14.95"},{"amount":"30","detail":"Regular Talktime","validity":"NA","talktime":"22.42"},{"amount":"50","detail":"Regular Talktime","validity":"NA","talktime":"39.37"},{"amount":"100","detail":"Regular Talktime","validity":"NA","talktime":"81.75"}],"RMG":[{"amount":"10","detail":"Regular Talktime","validity":"NA","talktime":"7.47"}]}
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
        private List<SPLBean> SPL;
        private List<DATABean> DATA;
        private List<FTTBean> FTT;
        private List<TUPBean> TUP;
        private List<RMGBean> RMG;

        public List<SPLBean> getSPL() {
            return SPL;
        }

        public void setSPL(List<SPLBean> SPL) {
            this.SPL = SPL;
        }

        public List<DATABean> getDATA() {
            return DATA;
        }

        public void setDATA(List<DATABean> DATA) {
            this.DATA = DATA;
        }

        public List<FTTBean> getFTT() {
            return FTT;
        }

        public void setFTT(List<FTTBean> FTT) {
            this.FTT = FTT;
        }

        public List<TUPBean> getTUP() {
            return TUP;
        }

        public void setTUP(List<TUPBean> TUP) {
            this.TUP = TUP;
        }

        public List<RMGBean> getRMG() {
            return RMG;
        }

        public void setRMG(List<RMGBean> RMG) {
            this.RMG = RMG;
        }

        public static class SPLBean {
            /**
             * amount : 12
             * detail : Get 120 Local/National SMS for 10 days
             * validity : 10 Days
             * talktime : NA
             */

            private String amount;
            private String detail;
            private String validity;
            private String talktime;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getValidity() {
                return validity;
            }

            public void setValidity(String validity) {
                this.validity = validity;
            }

            public String getTalktime() {
                return talktime;
            }

            public void setTalktime(String talktime) {
                this.talktime = talktime;
            }
        }

        public static class DATABean {
            /**
             * amount : 16
             * detail : Rs16: Get 1 GB 4G/3G/2G Data; Validity: 1 Day
             * validity : 1
             * talktime : NA
             */

            private String amount;
            private String detail;
            private String validity;
            private String talktime;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getValidity() {
                return validity;
            }

            public void setValidity(String validity) {
                this.validity = validity;
            }

            public String getTalktime() {
                return talktime;
            }

            public void setTalktime(String talktime) {
                this.talktime = talktime;
            }
        }

        public static class FTTBean {
            /**
             * amount : 10
             * detail : Regular Talktime
             * validity : NA
             * talktime : 7.47
             */

            private String amount;
            private String detail;
            private String validity;
            private String talktime;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getValidity() {
                return validity;
            }

            public void setValidity(String validity) {
                this.validity = validity;
            }

            public String getTalktime() {
                return talktime;
            }

            public void setTalktime(String talktime) {
                this.talktime = talktime;
            }
        }

        public static class TUPBean {
            /**
             * amount : 10
             * detail : Regular Talktime
             * validity : NA
             * talktime : 7.47
             */

            private String amount;
            private String detail;
            private String validity;
            private String talktime;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getValidity() {
                return validity;
            }

            public void setValidity(String validity) {
                this.validity = validity;
            }

            public String getTalktime() {
                return talktime;
            }

            public void setTalktime(String talktime) {
                this.talktime = talktime;
            }
        }

        public static class RMGBean {
            /**
             * amount : 10
             * detail : Regular Talktime
             * validity : NA
             * talktime : 7.47
             */

            private String amount;
            private String detail;
            private String validity;
            private String talktime;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getValidity() {
                return validity;
            }

            public void setValidity(String validity) {
                this.validity = validity;
            }

            public String getTalktime() {
                return talktime;
            }

            public void setTalktime(String talktime) {
                this.talktime = talktime;
            }
        }
    }
}
