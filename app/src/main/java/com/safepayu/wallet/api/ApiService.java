package com.safepayu.wallet.api;

import com.safepayu.wallet.models.request.AddBeneficiaryRequest;
import com.safepayu.wallet.models.request.BuyPackage;
import com.safepayu.wallet.models.request.ChangePasswordRequest;
import com.safepayu.wallet.models.request.CheckEmailMobileRequest;
import com.safepayu.wallet.models.request.ForgetPasswordRequest;
import com.safepayu.wallet.models.request.HashKeyRequest;
import com.safepayu.wallet.models.request.KycRequest;
import com.safepayu.wallet.models.request.Login;
import com.safepayu.wallet.models.request.PromotionRequest;
import com.safepayu.wallet.models.request.RechargeRequest;
import com.safepayu.wallet.models.request.RedeemCoinRequest;
import com.safepayu.wallet.models.request.Register;
import com.safepayu.wallet.models.request.ResetPasscodeModel;
import com.safepayu.wallet.models.request.SaveAddressRequest;
import com.safepayu.wallet.models.request.SaveCoinRequest;
import com.safepayu.wallet.models.request.SendOtpRequest;
import com.safepayu.wallet.models.request.SendPaymentGatewayDetailsRequest;
import com.safepayu.wallet.models.request.SendToWalletRequest;
import com.safepayu.wallet.models.request.TransferWalletToBankRequest;
import com.safepayu.wallet.models.request.UpdateAddress;
import com.safepayu.wallet.models.request.booking.flight.AvailableFlightRequest;
import com.safepayu.wallet.models.request.booking.flight.FlightBlockTicketRequest;
import com.safepayu.wallet.models.request.booking.flight.FlightBookingDetailRequest;
import com.safepayu.wallet.models.request.booking.flight.FlightHistoryResponse;
import com.safepayu.wallet.models.request.booking_bus.BusBlockingRequest;
import com.safepayu.wallet.models.request.booking_bus.BusListRequest;
import com.safepayu.wallet.models.request.booking_bus.BusTripDetailsRequest;
import com.safepayu.wallet.models.request.booking_hotel.AvailableHotelRequest;
import com.safepayu.wallet.models.request.booking_hotel.BookHotelRequest;
import com.safepayu.wallet.models.request.booking_hotel.HotelDetailsRequest;
import com.safepayu.wallet.models.response.AddBeneficiaryResponse;
import com.safepayu.wallet.models.response.AppVersionResponse;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.BuyPackageResponse;
import com.safepayu.wallet.models.response.CoinLogResponse;
import com.safepayu.wallet.models.response.CommissionDetailsResponse;
import com.safepayu.wallet.models.response.CommissionWalletTransferResponse;
import com.safepayu.wallet.models.response.CountryListResponse;
import com.safepayu.wallet.models.response.CustOperatorResponse;
import com.safepayu.wallet.models.response.ForgetPasswordResponse;
import com.safepayu.wallet.models.response.GetBeneficiaryResponse;
import com.safepayu.wallet.models.response.HashKeyResponse;
import com.safepayu.wallet.models.response.LoginResponse;
import com.safepayu.wallet.models.response.MyOrderResponse;
import com.safepayu.wallet.models.response.NotificationResponse;
import com.safepayu.wallet.models.response.OperatorResponse;
import com.safepayu.wallet.models.response.PackageDetailsResponse;
import com.safepayu.wallet.models.response.PackageListData;
import com.safepayu.wallet.models.response.PromotionResponse;
import com.safepayu.wallet.models.response.RechargeHistoryResponse;
import com.safepayu.wallet.models.response.RechargePlanResponse;
import com.safepayu.wallet.models.response.RechargeResponse;
import com.safepayu.wallet.models.response.RedeemCoinResponse;
import com.safepayu.wallet.models.response.ReferralCodeResponse;
import com.safepayu.wallet.models.response.SaveAddressResponse;
import com.safepayu.wallet.models.response.SaveCoinResponse;
import com.safepayu.wallet.models.response.SendPaymentGatewayDetailsResponse;
import com.safepayu.wallet.models.response.SendToWalletResponse;
import com.safepayu.wallet.models.response.StateListResponse;
import com.safepayu.wallet.models.response.TransferWalletToBankResponse;
import com.safepayu.wallet.models.response.UpdateAddressResponse;
import com.safepayu.wallet.models.response.UpiUserDetailsResponse;
import com.safepayu.wallet.models.response.UserDetailResponse;
import com.safepayu.wallet.models.response.UserResponse;
import com.safepayu.wallet.models.response.UserResponse1;
import com.safepayu.wallet.models.response.WalletHistoryResponse;
import com.safepayu.wallet.models.response.WalletLimitResponse;
import com.safepayu.wallet.models.response.WalletResponse;
import com.safepayu.wallet.models.response.booking.HotelDetailResponse;
import com.safepayu.wallet.models.response.booking.bus.BusBlockingResponse;
import com.safepayu.wallet.models.response.booking.bus.BusBookingResponse;
import com.safepayu.wallet.models.response.booking.bus.BusListResponse;
import com.safepayu.wallet.models.response.booking.bus.BusSourcesResponse;
import com.safepayu.wallet.models.response.booking.bus.BusTripDetailsResponse;
import com.safepayu.wallet.models.response.booking.flight.AirportLocationResponse;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;
import com.safepayu.wallet.models.response.booking.flight.CancelBookTicketResponse;
import com.safepayu.wallet.models.response.booking.flight.FlightBlockTicketResponse;
import com.safepayu.wallet.models.response.booking.flight.FlightBookingDetailResponse;
import com.safepayu.wallet.models.response.booking.hotel.AvailableHotelsResponse;
import com.safepayu.wallet.models.response.booking.hotel.HotelBookResponse;
import com.safepayu.wallet.models.response.booking.hotel.HotelSourcesResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
//    @Header("X-Bearer-Token") String authorization

    // Register new user
    @POST("api/secure.safepe/api/createUser")
    Single<UserResponse1> register(@Body Register registerRequest);

    // Login user
    @POST("api/secure.safepe/api/login")
    Single<LoginResponse> login(@Body Login loginRequest);
    // Resend OTP
    @POST("api/secure.safepe/api/resendOTP")
    Single<BaseResponse> resendOtp(@Body SendOtpRequest sendOtpRequest);
    // verify OTP
    @POST("api/secure.safepe/api/verifyOTP")
    Single<UserResponse> verifyOTP(@Body Login loginRequest);
    // Create PassCode
    @POST("api/secure.safepe/api/savePasscode")
    Single<UserDetailResponse> savePassCode(@Body Login loginRequest);
    // All Packages listing
    @GET("api/secure.safepe/api/getAllPackages")
    Single<PackageListData> getAllPackages();
    // All Packages listing
    @GET("api/secure.safepe/api/getUser")
    Single<UserDetailResponse> getUserDetails();

    @POST("api/secure.safepe/api/buyPackage")
    Single<BuyPackageResponse> buyPackage(@Body BuyPackage buyPackage);

    @POST("api/secure.safepe/api/resetPasscode")
    Single<BaseResponse> resetPasscode(@Body ResetPasscodeModel resetPasscodeModel);

    @POST("api/secure.safepe/api/saveBeneficiary")
    Single<AddBeneficiaryResponse> addBeneficiary(@Body AddBeneficiaryRequest addBeneficiaryRequest);

    @POST("api/secure.safepe/api/getBeneficiaryDetails")
    Single<GetBeneficiaryResponse> getBeneficiary();;

    @POST("api/secure.safepe/api/transferWalletToBank")
    Single<TransferWalletToBankResponse> transferWalletToBank(@Body TransferWalletToBankRequest transferWalletToBankRequest);

    @POST("api/secure.safepe/api/updateUserAddress")
    Single<UpdateAddressResponse>updateAddress(@Body UpdateAddress updateAddress);

    @POST("api/secure.safepe/api/changePassword")
    Single<UserResponse>changePwd(@Body ChangePasswordRequest changePassword);

    @POST("api/secure.safepe/api/walletToWallet")
    Single<SendToWalletResponse> transferWalletToWallet(@Body SendToWalletRequest sendToWalletRequest);

    @POST("api/secure.safepe/api/getWalletDetails")
    Single<WalletResponse> getWalletDetails();

    @POST("api/secure.safepe/api/getLatestUserBuyPackage")
    Single<PackageDetailsResponse> getPackageDetails();

    @POST("api/secure.safepe/api/recharge")
    Single<RechargeResponse> doRecharge(@Body RechargeRequest rechargeRequest);

    @POST("api/secure.safepe/api/hasKey")
    Single<HashKeyResponse> getHashKey(@Body HashKeyRequest hashKeyRequest);

    @FormUrlEncoded
    @POST("api/secure.safepe/api/getAllOperators")
    Single<OperatorResponse> getOperators(@Field("operator_type") String operator_type);

    @FormUrlEncoded
    @POST("api/secure.safepe/api/getCustOperator")
    Single<CustOperatorResponse> getMobileOperator(@Field("number") String number);

    @FormUrlEncoded
    @POST("api/secure.safepe/api/rechargePlan")
    Single<RechargePlanResponse> getRechargePlan(@Field("mobile") String mobile, @Field("opId") String opId);

    @POST("api/secure.safepe/api/passwordNewCreate")
    Single<ForgetPasswordResponse> getForgetPassword(@Body ForgetPasswordRequest forgetPasswordResponse);

    @POST("api/secure.safepe/api/addBankToWallet")
    Single<SendPaymentGatewayDetailsResponse> addBankToWallet(@Body SendPaymentGatewayDetailsRequest sendPaymentGatewayDetailsRequest);

    @FormUrlEncoded
    @POST("api/secure.safepe/api/commitionToWallet")
    Single<CommissionWalletTransferResponse> transferCommWalletToMainWallet(@Field("amount") String amount);

    @FormUrlEncoded
    @POST("api/secure.safepe/api/findVenificairyName")
    Single<ReferralCodeResponse> getReferralDetails(@Field("referral_code") String referral_code, @Field("type") String type, @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("api/secure.safepe/api/sendVerifyEmailLink")
    Single<BaseResponse> verifyEmail(@Field("userid") String userid,@Field("email") String email);

    @GET("api/secure.safepe/api/rechargeHistory")
    Single<RechargeHistoryResponse> getRechargeHistory();

    @GET("api/secure.safepe/api/wallletHistory")
    Single<WalletHistoryResponse> getWalletHistory();

    @GET("api/secure.safepe/api/getVersion")
    Single<AppVersionResponse> getAppVersion();

    @FormUrlEncoded
    @POST("api/secure.safepe/api/refer")
    Single<BaseResponse> sendRefer(@Field("mobile") String mobile);

    @POST("api/secure.safepe/api/SaveUserAddress")
    Single<SaveAddressResponse> addAddress(@Body SaveAddressRequest saveAddressRequest);

    @POST("api/secure.safepe/api/getCommitionWithBusinessAmount")
    Single<CommissionDetailsResponse> getCommissionDetails();

    @FormUrlEncoded
    @POST("api/secure.safepe/api/getUserDetails")
    Single<UpiUserDetailsResponse> getUserDetailUPI(@Field("userid") String userid);

    @POST("api/secure.safepe/api/checkUserMobile")
    Single<BaseResponse> checkEmailMobile(@Body CheckEmailMobileRequest checkEmailMobileRequest );

    @FormUrlEncoded
    @POST("api/secure.safepe/api/getFirebaseTocken")
    Single<BaseResponse> getFirebaseToken(@Field("userid") String userid, @Field("token") String token);

    @GET("api/secure.safepe/api/getNotificationData")
    Single<NotificationResponse> getNotificationData();

    @POST("api/secure.safepe/api/logoutAlldevices")
    Single<BaseResponse> getlogoutAlldevices();

    @GET("api/secure.safepe/api/bankPaymentList")
    Single<MyOrderResponse>getBankPayment();

    @GET("api/secure.safepe/api/checkBuyPackage")
    Single<BaseResponse> getcheckBuyPackage();

    @POST("api/secure.safepe/api/promotionalImages")
    Single<PromotionResponse>getPromotionOffer(@Body PromotionRequest promotionRequest);

    @POST("api/secure.safepe/api/registerKyc")
    Single<BaseResponse> getKYCDone(@Body KycRequest kycRequest);

    @GET("api/secure.safepe/api/statusKyc")
    Single<BaseResponse> getKycCheck();

    @GET("api/secure.safepe/api/countryList")
    Single<CountryListResponse> getCountryList();

    @FormUrlEncoded
    @POST("api/secure.safepe/api/stateList")
    Single<StateListResponse> getStateList(@Field("country_id") String country_id);

    @GET("api/secure.safepe/api/walletLimitLeft")
    Single<WalletLimitResponse>getWalletLimitLeft();

    @FormUrlEncoded
    @POST("api/secure.safepe/api/verifyPasscode")
    Single<BaseResponse> getPasscodeVerify(@Field("passcode") String passcode);



    //*************Flight Booking *******************//
    @GET("api/secure.safepe/api/getFlightAirport")
    Single<AirportLocationResponse> getAirportLocation();

    @POST("api/secure.safepe/api/getFlightAvailable")
    Single<AvailableFlightResponse> getAvailableFlight(@Body AvailableFlightRequest availableFlightRequest);

    @POST("api/secure.safepe/api/getFlightBlockTicket ")
    Single<FlightBlockTicketResponse> getFlightBlockTicket(@Body FlightBlockTicketRequest flightBlockTicketRequest);

    @POST("api/secure.safepe/api/getFlightBookingDetails")
    Single<FlightBookingDetailResponse> getFlightBookingDetails(@Body FlightBookingDetailRequest flightBookingDetailRequest);

    @POST("api/secure.safepe/api/getFlightCancelTicket")
    Single<CancelBookTicketResponse> getFlightCancelTicket(@Body FlightBookingDetailRequest flightBookingDetailRequest);

    @GET("api/secure.safepe/api/getFlightHistory")
    Single<FlightHistoryResponse> getFlightHistory();


    //*************Bus Booking *******************//
    @POST("api/secure.safepe/api/postBusLocationList")
    Single<BusSourcesResponse> getBusSourcres();

    @POST("api/secure.safepe/api/postBusAvailable")
    Single<BusListResponse> getBusAvailable(@Body BusListRequest busListRequest);

    @POST("api/secure.safepe/api/postBusTripDetails")
    Single<BusTripDetailsResponse> getBusTripDetails(@Body BusTripDetailsRequest busTripDetailsRequest);

    @POST("api/secure.safepe/api/postBusTripDetails")
    Single<BusBlockingResponse> getBusBlocking(@Body BusBlockingRequest busBookingRequest);

    @FormUrlEncoded
    @POST("api/secure.safepe/api/getFirebaseTocken")
    Single<BusBookingResponse> getBookingBus(@Field("referenceNo") String referenceNo);


    //*************Hotel Booking *******************//
    @POST("api/secure.safepe/api/hotelSources")
    Single<HotelSourcesResponse> getHotelSources();

    @POST("api/secure.safepe/api/hotelAvailable")
    Single<AvailableHotelsResponse> getHotelAvailable(@Body AvailableHotelRequest availableHotelRequest);

    @POST("api/secure.safepe/api/hotelDetails")
    Single<HotelDetailResponse> getHotelDetails(@Body HotelDetailsRequest hotelDetailsRequest);

    @POST("api/secure.safepe/api/hotelBlock")
    Single<HotelBookResponse> getHotelBook(@Body BookHotelRequest bookHotelRequest);

    //*************Reward Coin *******************//
    @GET("api/secure.safepe/api/coinLog")
    Single<CoinLogResponse> getcoinLog();

    @POST("api/secure.safepe/api/saveCoin")
    Single<SaveCoinResponse> getSaveCoin(@Body SaveCoinRequest saveCoinRequest);

    @POST("api/secure.safepe/api/redeemCoin")
    Single<RedeemCoinResponse> getRedeemCoin(@Body RedeemCoinRequest redeemCoinRequest);
}
