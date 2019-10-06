package com.safepayu.wallet.api;

import com.safepayu.wallet.models.request.AddBeneficiaryRequest;
import com.safepayu.wallet.models.request.BuyPackage;
import com.safepayu.wallet.models.request.ChangePassword;
import com.safepayu.wallet.models.request.ForgetPasswordRequest;
import com.safepayu.wallet.models.request.HashKeyRequest;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.request.RechargeRequest;
import com.safepayu.wallet.models.request.Register;
import com.safepayu.wallet.models.request.ResetPasscodeModel;
import com.safepayu.wallet.models.request.SendPaymentGatewayDetailsRequest;
import com.safepayu.wallet.models.request.SendToWalletRequest;
import com.safepayu.wallet.models.request.TransferWalletToBankRequest;
import com.safepayu.wallet.models.request.UpdateAddress;
import com.safepayu.wallet.models.response.AddBeneficiaryResponse;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.BuyPackageResponse;
import com.safepayu.wallet.models.response.CustOperatorResponse;
import com.safepayu.wallet.models.response.GetBeneficiaryResponse;
import com.safepayu.wallet.models.response.HashKeyResponse;
import com.safepayu.wallet.models.response.LoginResponse;
import com.safepayu.wallet.models.response.OperatorResponse;
import com.safepayu.wallet.models.response.PackageDetailsResponse;
import com.safepayu.wallet.models.response.PackageListData;
import com.safepayu.wallet.models.response.ReferralCodeResponse;
import com.safepayu.wallet.models.response.SendPaymentGatewayDetailsResponse;
import com.safepayu.wallet.models.response.TransferWalletToBankResponse;
import com.safepayu.wallet.models.response.UpdateAddressResponse;
import com.safepayu.wallet.models.response.UserResponse;
import com.safepayu.wallet.models.response.WalletResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
//    @Header("X-Bearer-Token") String authorization

    // Register new user
    @POST("api/safepe/createUser")
    Single<UserResponse> register(@Body Register registerRequest);

    // Login user
    @POST("api/safepe/login")
    Single<LoginResponse> login(@Body Login loginRequest);
    // Resend OTP
    @POST("api/safepe/resendOTP")
    Single<BaseResponse> resendOtp(@Body Login loginRequest);
    // verify OTP
    @POST("api/safepe/verifyOTP")
    Single<UserResponse> verifyOTP(@Body Login loginRequest);
    // Create PassCode
    @POST("api/safepe/savePasscode")
    Single<UserResponse> savePassCode(@Body Login loginRequest);
    // All Packages listing
    @GET("api/safepe/getAllPackages")
    Single<PackageListData> getAllPackages();
    // All Packages listing
    @GET("api/safepe/getUser")
    Single<UserResponse> getUserDetails();

    @POST("api/safepe/buyPackage")
    Single<BuyPackageResponse> buyPackage(@Body BuyPackage buyPackage);

    @POST("api/safepe/resetPasscode")
    Single<BaseResponse> resetPasscode(@Body ResetPasscodeModel resetPasscodeModel);

    @POST("api/safepe/saveBeneficiary")
    Single<AddBeneficiaryResponse> addBeneficiary(@Body AddBeneficiaryRequest addBeneficiaryRequest);

    @POST("api/safepe/getBeneficiaryDetails")
    Single<GetBeneficiaryResponse> getBeneficiary();;

    @POST("api/safepe/transferWalletToBank")
    Single<TransferWalletToBankResponse> transferWalletToBank(@Body TransferWalletToBankRequest transferWalletToBankRequest);

    @POST("api/safepe/updateUserAddress")
    Single<UpdateAddressResponse>updateAddress(@Body UpdateAddress updateAddress);

    @POST("api/safepe/changePassword")
    Single<UserResponse>changePwd(@Body ChangePassword changePassword);

    @POST("api/safepe/walletToWallet")
    Single<BaseResponse> transferWalletToWallet(@Body SendToWalletRequest sendToWalletRequest);

    @POST("api/safepe/getWalletDetails")
    Single<WalletResponse> getWalletDetails();;

    @POST("api/safepe/getLatestUserBuyPackage")
    Single<PackageDetailsResponse> getPackageDetails();

    @POST("api/safepe/recharge")
    Single<BaseResponse> doRecharge(@Body RechargeRequest rechargeRequest);

    @POST("api/safepe/hasKey")
    Single<HashKeyResponse> getHashKey(@Body HashKeyRequest hashKeyRequest);

    @FormUrlEncoded
    @POST("api/safepe/getAllOperators")
    Single<OperatorResponse> getOperators(@Field("operator_type") String operator_type);

    @FormUrlEncoded
    @POST("api/safepe/getCustOperator")
    Single<CustOperatorResponse> getMobileOperator(@Field("number") String number);

    @POST("api/safepe/forgotPassword")
    Single<BaseResponse> getForgetPassword(@Body ForgetPasswordRequest forgetPasswordResponse);

    @POST("api/safepe/addBankToWallet")
    Single<SendPaymentGatewayDetailsResponse> addBankToWallet(@Body SendPaymentGatewayDetailsRequest sendPaymentGatewayDetailsRequest);

    @FormUrlEncoded
    @POST("api/safepe/commitionToWallet")
    Single<BaseResponse> transferCommWalletToMainWallet(@Field("amount") String amount);

    @FormUrlEncoded
    @POST("api/safepe/findVenificairyName")
    Single<ReferralCodeResponse> getReferralDetails(@Field("referral_code") String referral_code);

    @FormUrlEncoded
    @POST("api/safepe/sendVerifyEmailLink")
    Single<BaseResponse> verifyEmail(@Field("email") String email);
}
