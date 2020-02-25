package com.safepayu.wallet.models.response;

public class UserDetailResponse {

    /**
     * status : true
     * message : User Details.
     * user : {"id":1177,"userid":"u9454756926","first_name":"Sandeep","last_name":"Kumar","email":"sandeepkumarsingh.1bca@gmail.com","email_verified_at":null,"email_verified":1,"mobile":"9454756926","mobile_verified":"1","dob":"1992-05-05","referral_code":"9454756926","referral_recieved":"honzd7vm","user_sponsor":"u8465885421","reg_time":"2019-10-13 00:00:00","blocked":1,"is_binary":0,"passcode":2525,"user_type":1,"otp":4388,"otp_time":"16:36:30","device_type":null,"device_id":null,"capp_amount":"0.00","limit_per_day":"8000.00","status":1,"package_status":1,"created_at":"2019-10-13 00:00:00","updated_at":"2019-10-13 16:39:26","location":"varansi","city":"Ghaziabad","state":"Uttar Pradesh","country":"India","pin":201009}
     */

    private boolean status;
    private String message;
    private UserBean user;

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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * id : 1177
         * userid : u9454756926
         * first_name : Sandeep
         * last_name : Kumar
         * email : sandeepkumarsingh.1bca@gmail.com
         * email_verified_at : null
         * email_verified : 1
         * mobile : 9454756926
         * mobile_verified : 1
         * dob : 1992-05-05
         * referral_code : 9454756926
         * referral_recieved : honzd7vm
         * user_sponsor : u8465885421
         * reg_time : 2019-10-13 00:00:00
         * blocked : 1
         * is_binary : 0
         * passcode : 2525
         * user_type : 1
         * otp : 4388
         * otp_time : 16:36:30
         * device_type : null
         * device_id : null
         * capp_amount : 0.00
         * limit_per_day : 8000.00
         * status : 1
         * package_status : 1
         * created_at : 2019-10-13 00:00:00
         * updated_at : 2019-10-13 16:39:26
         * location : varansi
         * city : Ghaziabad
         * state : Uttar Pradesh
         * country : India
         * pin : 201009
         *
         */



        private int id;
        private String userid;
        private String first_name;
        private String last_name;
        private String email;
        private Object email_verified_at;
        private int email_verified;
        private String mobile;
        private String mobile_verified;
        private String dob;
        private String referral_code;
        private String referral_recieved;// sponser_id
        private String user_sponsor;
        private String reg_time;
        private int blocked;
        private int is_binary;
        private int passcode;
        private int user_type;
        private int otp;
        private String otp_time;
        private Object device_type;
        private Object device_id;
        private String capp_amount;
        private String limit_per_day;
        private int status;
        private int package_status;
        private String created_at;
        private String updated_at;
        private String location;
        private String city;
        private String state;
        private String country;
        private int pin;
        private int remember_me;
        private String sponsorname;
        /**
         * genealogy : http://productiontesting.safepeindia.com/Genealogy/
         * menuBuyPackage_status : 0
         * payment_screen : 0
         */


        private String genealogy;
        private int menuBuyPackage_status;
        private int payment_screen;
        /**
         * wallet_amount : 1600.00
         */

        private String wallet_amount;

        public String getSponsorname() {
            return sponsorname;
        }

        public void setSponsorname(String sponsorname) {
            this.sponsorname = sponsorname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getEmail_verified_at() {
            return email_verified_at;
        }

        public void setEmail_verified_at(Object email_verified_at) {
            this.email_verified_at = email_verified_at;
        }

        public int getEmail_verified() {
            return email_verified;
        }

        public void setEmail_verified(int email_verified) {
            this.email_verified = email_verified;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile_verified() {
            return mobile_verified;
        }

        public void setMobile_verified(String mobile_verified) {
            this.mobile_verified = mobile_verified;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getReferral_code() {
            return referral_code;
        }

        public void setReferral_code(String referral_code) {
            this.referral_code = referral_code;
        }

        public String getReferral_recieved() {
            return referral_recieved;
        }

        public void setReferral_recieved(String referral_recieved) {
            this.referral_recieved = referral_recieved;
        }

        public String getUser_sponsor() {
            return user_sponsor;
        }

        public void setUser_sponsor(String user_sponsor) {
            this.user_sponsor = user_sponsor;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public int getBlocked() {
            return blocked;
        }

        public void setBlocked(int blocked) {
            this.blocked = blocked;
        }

        public int getIs_binary() {
            return is_binary;
        }

        public void setIs_binary(int is_binary) {
            this.is_binary = is_binary;
        }

        public int getPasscode() {
            return passcode;
        }

        public void setPasscode(int passcode) {
            this.passcode = passcode;
        }

        public int getUser_type() {
            return user_type;
        }

        public void setUser_type(int user_type) {
            this.user_type = user_type;
        }

        public int getOtp() {
            return otp;
        }

        public void setOtp(int otp) {
            this.otp = otp;
        }

        public String getOtp_time() {
            return otp_time;
        }

        public void setOtp_time(String otp_time) {
            this.otp_time = otp_time;
        }

        public Object getDevice_type() {
            return device_type;
        }

        public void setDevice_type(Object device_type) {
            this.device_type = device_type;
        }

        public Object getDevice_id() {
            return device_id;
        }

        public void setDevice_id(Object device_id) {
            this.device_id = device_id;
        }

        public String getCapp_amount() {
            return capp_amount;
        }

        public void setCapp_amount(String capp_amount) {
            this.capp_amount = capp_amount;
        }

        public String getLimit_per_day() {
            return limit_per_day;
        }

        public void setLimit_per_day(String limit_per_day) {
            this.limit_per_day = limit_per_day;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPackage_status() {
            return package_status;
        }

        public void setPackage_status(int package_status) {
            this.package_status = package_status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getPin() {
            return pin;
        }

        public void setPin(int pin) {
            this.pin = pin;
        }

        public int getRemember_me() {
            return remember_me;
        }

        public void setRemember_me(int remember_me) {
            this.remember_me = remember_me;
        }

        public String getGenealogy() {
            return genealogy;
        }

        public void setGenealogy(String genealogy) {
            this.genealogy = genealogy;
        }

        public int getMenuBuyPackage_status() {
            return menuBuyPackage_status;
        }

        public void setMenuBuyPackage_status(int menuBuyPackage_status) {
            this.menuBuyPackage_status = menuBuyPackage_status;
        }

        public int getPayment_screen() {
            return payment_screen;
        }

        public void setPayment_screen(int payment_screen) {
            this.payment_screen = payment_screen;
        }

        public String getWallet_amount() {
            return wallet_amount;
        }

        public void setWallet_amount(String wallet_amount) {
            this.wallet_amount = wallet_amount;
        }
    }

}
