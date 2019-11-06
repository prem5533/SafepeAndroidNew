package com.safepayu.wallet.models.response;

public class AppVersionResponse {

    /**
     * status : true
     * message : App Vesion code.
     * versionData : {"version_name":"1.0","val":"1"}
     */

    private boolean status;
    private String message;
    private VersionDataBean versionData;

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

    public VersionDataBean getVersionData() {
        return versionData;
    }

    public void setVersionData(VersionDataBean versionData) {
        this.versionData = versionData;
    }

    public static class VersionDataBean {
        /**
         * version_name : 1.0
         * val : 1
         */

        private String version_name;
        private String val;
        /**
         * tollfree : 0090909
         */

        private String tollfree;


        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getTollfree() {
            return tollfree;
        }

        public void setTollfree(String tollfree) {
            this.tollfree = tollfree;
        }
    }
}
