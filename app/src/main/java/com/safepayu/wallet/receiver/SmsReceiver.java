package com.safepayu.wallet.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.safepayu.wallet.helper.Config;
import com.safepayu.wallet.service.OtpVerifyService;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                    // if the SMS is not from our gateway, ignore the message
                    if (!senderAddress.toLowerCase().contains(Config.SMS_ORIGIN.toLowerCase())) {
                        return;
                    }

                    // verification code from sms
                    String verificationCode = getVerificationCode(message);

                    Log.e(TAG, "OTP received: " + verificationCode);

                    Intent hhtpIntent = new Intent(context, OtpVerifyService.class);
                    hhtpIntent.putExtra(Config.OTP, verificationCode);
                    context.startService(hhtpIntent);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Getting the OTP from sms message body
     * ':' is the separator of OTP from the message
     *
     * @param message
     * @return
     */
    private String getVerificationCode(String message) {
        String code = null;
//        int index = message.indexOf(Config.OTP_DELIMITER);
        int index = message.length();

        if (index != -1) {
            int start = 0;//index + 2;
            int length = 4;
            code = message.substring(start, start + length);
            return code;
        }

        return code;
    }
}