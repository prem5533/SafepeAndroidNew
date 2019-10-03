package com.safepayu.wallet.api;

import com.safepayu.wallet.models.request.AddBeneficiaryRequest;
import com.safepayu.wallet.models.request.BuyPackage;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.request.Register;
import com.safepayu.wallet.models.request.ResetPasscodeModel;
import com.safepayu.wallet.models.request.TransferWalletToBankRequest;
import com.safepayu.wallet.models.response.AddBeneficiaryResponse;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.BuyPackageResponse;
import com.safepayu.wallet.models.response.GetBeneficiaryResponse;
import com.safepayu.wallet.models.response.LoginResponse;
import com.safepayu.wallet.models.response.PackageListData;
import com.safepayu.wallet.models.response.UserResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
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
    Single<BaseResponse> transferWalletToBank(@Body TransferWalletToBankRequest transferWalletToBankRequest);
}
