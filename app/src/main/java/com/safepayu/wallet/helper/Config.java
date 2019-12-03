package com.safepayu.wallet.helper;

public class Config {
    public static final String SMS_ORIGIN = "VM-TFCTOR";
    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";
    public static final String OTP_VERIFICATION = "otpVerification";
    public static final String OTP_VERIFICATION_STATUS = "otpVerificationStatus";
    public static final String OTP = "otp";
    public static final String MOBILE_NO = "mobileNo";
}
