package com.safepayu.wallet.api;

import com.safepayu.wallet.models.request.AddBeneficiaryRequest;
import com.safepayu.wallet.models.request.BuyPackage;
import com.safepayu.wallet.models.request.ChangePasswordRequest;
import com.safepayu.wallet.models.request.CheckEmailMobileRequest;
import com.safepayu.wallet.models.request.FDPayRequest;
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
import com.safepayu.wallet.models.request.booking.flight.ConvieneceFeeRequest;
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
import com.safepayu.wallet.models.response.InvestmentReferResponse;
import com.safepayu.wallet.models.response.InvestmentResponse;
import com.safepayu.wallet.models.response.InvestmentWalletLogResponse;
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
import com.safepayu.wallet.models.response.ResponseModel;
import com.safepayu.wallet.models.response.SaveAddressResponse;
import com.safepayu.wallet.models.response.SaveCoinResponse;
import com.safepayu.wallet.models.response.SendPaymentGatewayDetailsResponse;
import com.safepayu.wallet.models.response.SendToWalletResponse;
import com.safepayu.wallet.models.response.ServiceChargeResponse;
import com.safepayu.wallet.models.response.StateListResponse;
import com.safepayu.wallet.models.response.TransferWalletToBankResponse;
import com.safepayu.wallet.models.response.UpdateAddressResponse;
import com.safepayu.wallet.models.response.UpiUserDetailsResponse;
import com.safepayu.wallet.models.response.UserDetailResponse;
import com.safepayu.wallet.models.response.UserResponse;
import com.safepayu.wallet.models.response.UserResponse1;
import com.safepayu.wallet.models.response.VerifyIFSCResponse;
import com.safepayu.wallet.models.response.WalletHistoryResponse;
import com.safepayu.wallet.models.response.WalletLimitResponse;
import com.safepayu.wallet.models.response.WalletResponse;
import com.safepayu.wallet.models.response.booking.bus.BusBlockingResponse;
import com.safepayu.wallet.models.response.booking.bus.BusBookingResponse;
import com.safepayu.wallet.models.response.booking.bus.BusCancelResponse;
import com.safepayu.wallet.models.response.booking.bus.BusHistoryResponse;
import com.safepayu.wallet.models.response.booking.bus.BusListResponse;
import com.safepayu.wallet.models.response.booking.bus.BusSourcesResponse;
import com.safepayu.wallet.models.response.booking.bus.BusTripDetailsResponse;
import com.safepayu.wallet.models.response.booking.bus.ConvenienceFeeResponse;
import com.safepayu.wallet.models.response.booking.flight.AirportLocationResponse;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;
import com.safepayu.wallet.models.response.booking.flight.CancelBookTicketResponse;
import com.safepayu.wallet.models.response.booking.flight.ConvieneceFeeResponse;
import com.safepayu.wallet.models.response.booking.flight.FlighPdfResponse;
import com.safepayu.wallet.models.response.booking.flight.FlightBlockTicketResponse;
import com.safepayu.wallet.models.response.booking.flight.FlightBookingDetailResponse;
import com.safepayu.wallet.models.response.booking.hotel.AvailableHotelsResponse;
import com.safepayu.wallet.models.response.booking.hotel.HotelBookResponse;
import com.safepayu.wallet.models.response.booking.hotel.HotelCancelResponse;
import com.safepayu.wallet.models.response.booking.hotel.HotelDetailResponse;
import com.safepayu.wallet.models.response.booking.hotel.HotelHistoryResponse;
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
    @POST("api/pefast.safepe.latepe/api/createUser")
    Single<UserResponse1> register(@Body Register registerRequest);

    // Login user
    @POST("api/pefast.safepe.latepe/api/login")
    Single<LoginResponse> login(@Body Login loginRequest);

    // Resend OTP
    @POST("api/pefast.safepe.latepe/api/resendOTP")
    Single<BaseResponse> resendOtp(@Body SendOtpRequest sendOtpRequest);

    // verify OTP
    @POST("api/pefast.safepe.latepe/api/verifyOTP")
    Single<UserResponse> verifyOTP(@Body Login loginRequest);

    // Create PassCode
    @POST("api/pefast.safepe.latepe/api/savePasscode")
    Single<UserDetailResponse> savePassCode(@Body Login loginRequest);

    // All Packages listing
    @GET("api/pefast.safepe.latepe/api/getAllPackages")
    Single<PackageListData> getAllPackages();

    // All Packages listing
    @GET("api/pefast.safepe.latepe/api/getUser")
    Single<UserDetailResponse> getUserDetails();

    @POST("api/pefast.safepe.latepe/api/buyPackage")
    Single<BuyPackageResponse> buyPackage(@Body BuyPackage buyPackage);

    @POST("api/pefast.safepe.latepe/api/resetPasscode")
    Single<BaseResponse> resetPasscode(@Body ResetPasscodeModel resetPasscodeModel);

    @POST("api/pefast.safepe.latepe/api/saveBeneficiary")
    Single<AddBeneficiaryResponse> addBeneficiary(@Body AddBeneficiaryRequest addBeneficiaryRequest);

    @POST("api/pefast.safepe.latepe/api/getBeneficiaryDetails")
    Single<GetBeneficiaryResponse> getBeneficiary();

    ;

    @POST("api/pefast.safepe.latepe/api/transferWalletToBank")
    Single<TransferWalletToBankResponse> transferWalletToBank(@Body TransferWalletToBankRequest transferWalletToBankRequest);

    @POST("api/pefast.safepe.latepe/api/updateUserAddress")
    Single<UpdateAddressResponse> updateAddress(@Body UpdateAddress updateAddress);

    @POST("api/pefast.safepe.latepe/api/changePassword")
    Single<UserResponse> changePwd(@Body ChangePasswordRequest changePassword);

    @POST("api/pefast.safepe.latepe/api/walletToWallet")
    Single<SendToWalletResponse> transferWalletToWallet(@Body SendToWalletRequest sendToWalletRequest);

    @POST("api/pefast.safepe.latepe/api/getWalletDetails")
    Single<WalletResponse> getWalletDetails();

    @POST("api/pefast.safepe.latepe/api/getLatestUserBuyPackage")
    Single<PackageDetailsResponse> getPackageDetails();

    @POST("api/pefast.safepe.latepe/api/recharge")
    Single<RechargeResponse> doRecharge(@Body RechargeRequest rechargeRequest);

    @POST("api/pefast.safepe.latepe/api/hasKey")
    Single<HashKeyResponse> getHashKey(@Body HashKeyRequest hashKeyRequest);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/getAllOperators")
    Single<OperatorResponse> getOperators(@Field("operator_type") String operator_type);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/updateProfile")
    Single<BaseResponse> getProfileUpdate(@Field("dob") String dob, @Field("email") String email);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/getCustOperator")
    Single<CustOperatorResponse> getMobileOperator(@Field("number") String number);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/rechargePlan")
    Single<RechargePlanResponse> getRechargePlan(@Field("mobile") String mobile, @Field("opId") String opId);

    @POST("api/pefast.safepe.latepe/api/passwordNewCreate")
    Single<ForgetPasswordResponse> getForgetPassword(@Body ForgetPasswordRequest forgetPasswordResponse);

    @POST("api/pefast.safepe.latepe/api/addBankToWallet")
    Single<SendPaymentGatewayDetailsResponse> addBankToWallet(@Body SendPaymentGatewayDetailsRequest sendPaymentGatewayDetailsRequest);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/commitionToWallet")
    Single<CommissionWalletTransferResponse> transferCommWalletToMainWallet(@Field("amount") String amount);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/findVenificairyName")
    Single<ReferralCodeResponse> getReferralDetails(@Field("referral_code") String referral_code, @Field("type") String type, @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/sendVerifyEmailLink")
    Single<BaseResponse> verifyEmail(@Field("userid") String userid, @Field("email") String email);

    @GET("api/pefast.safepe.latepe/api/rechargeHistory")
    Single<RechargeHistoryResponse> getRechargeHistory();

    @GET("api/pefast.safepe.latepe/api/wallletHistory")
    Single<WalletHistoryResponse> getWalletHistory();

    @GET("api/pefast.safepe.latepe/api/getVersion")
    Single<AppVersionResponse> getAppVersion();

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/refer")
    Single<BaseResponse> sendRefer(@Field("mobile") String mobile);

    @POST("api/pefast.safepe.latepe/api/SaveUserAddress")
    Single<SaveAddressResponse> addAddress(@Body SaveAddressRequest saveAddressRequest);

    @POST("api/pefast.safepe.latepe/api/getCommitionWithBusinessAmount")
    Single<CommissionDetailsResponse> getCommissionDetails();

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/getUserDetails")
    Single<UpiUserDetailsResponse> getUserDetailUPI(@Field("userid") String userid);

    @POST("api/pefast.safepe.latepe/api/checkUserMobile")
    Single<BaseResponse> checkEmailMobile(@Body CheckEmailMobileRequest checkEmailMobileRequest);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/getFirebaseTocken")
    Single<BaseResponse> getFirebaseToken(@Field("userid") String userid, @Field("token") String token);

    @GET("api/pefast.safepe.latepe/api/getNotificationData")
    Single<NotificationResponse> getNotificationData();

    @POST("api/pefast.safepe.latepe/api/logoutAlldevices")
    Single<BaseResponse> getlogoutAlldevices();

    @GET("api/pefast.safepe.latepe/api/bankPaymentList")
    Single<MyOrderResponse> getBankPayment();

    @GET("api/pefast.safepe.latepe/api/checkBuyPackage")
    Single<BaseResponse> getcheckBuyPackage();

    @POST("api/pefast.safepe.latepe/api/promotionalImages")
    Single<PromotionResponse> getPromotionOffer(@Body PromotionRequest promotionRequest);

    @POST("api/pefast.safepe.latepe/api/registerKyc")
    Single<BaseResponse> getKYCDone(@Body KycRequest kycRequest);

    @GET("api/pefast.safepe.latepe/api/statusKyc")
    Single<BaseResponse> getKycCheck();

    @GET("api/pefast.safepe.latepe/api/countryList")
    Single<CountryListResponse> getCountryList();

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/stateList")
    Single<StateListResponse> getStateList(@Field("country_id") String country_id);

    @GET("api/pefast.safepe.latepe/api/walletLimitLeft")
    Single<WalletLimitResponse> getWalletLimitLeft();

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/verifyPasscode")
    Single<BaseResponse> getPasscodeVerify(@Field("passcode") String passcode);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/getConvieneceFee")
    Single<ConvenienceFeeResponse> getConvieneceFee(@Field("type") String type);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/ifscUrl")
    Single<VerifyIFSCResponse> getifscVerify(@Field("ifsc_code") String ifsc_code);

    @GET("api/pefast.safepe.latepe/api/getServicesCharges")
    Single<ServiceChargeResponse> getServicesCharges();

    @POST("api/pefast.safepe.latepe/api/saveInvestment ")
    Single<BuyPackageResponse> saveInvestment (@Body FDPayRequest fdPayRequest);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/getInvestmentRefer")
    Single<InvestmentReferResponse> getInvestmentRefer(@Field("refer") String refer);

    @GET("api/pefast.safepe.latepe/api/getInvestmentLog")
    Single<InvestmentWalletLogResponse> getInvestmentLog();

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/investmentToWallet")
    Single<CommissionWalletTransferResponse> transferInvestmentToWallet(@Field("amount") String amount);

    //*************Flight Booking *******************//
    @GET("api/pefast.safepe.latepe/api/getFlightAirport")
    Single<AirportLocationResponse> getAirportLocation();

    @POST("api/pefast.safepe.latepe/api/getFlightAvailable")
    Single<AvailableFlightResponse> getAvailableFlight(@Body AvailableFlightRequest availableFlightRequest);

    @POST("api/pefast.safepe.latepe/api/getFlightBlockTicket ")
    Single<FlightBlockTicketResponse> getFlightBlockTicket(@Body FlightBlockTicketRequest flightBlockTicketRequest);

    @POST("api/pefast.safepe.latepe/api/getFlightBookingDetails")
    Single<FlightBookingDetailResponse> getFlightBookingDetails(@Body FlightBookingDetailRequest flightBookingDetailRequest);

    @POST("api/pefast.safepe.latepe/api/getFlightCancelTicket")
    Single<CancelBookTicketResponse> getFlightCancelTicket(@Body FlightBookingDetailRequest flightBookingDetailRequest);

    @GET("api/pefast.safepe.latepe/api/getFlightHistory")
    Single<FlightHistoryResponse> getFlightHistory();

    @POST("api/pefast.safepe.latepe/api/getFlightPdf")
    Single<FlighPdfResponse> getFlightPdf(@Body FlightBookingDetailRequest flightBookingDetailRequest);

    @POST("api/pefast.safepe.latepe/api/getConvieneceFee")
    Single<ConvieneceFeeResponse> getConvieneceFee(@Body ConvieneceFeeRequest convieneceFeeRequest);

    //*************Bus Booking *******************//
    @POST("api/pefast.safepe.latepe/api/postBusLocationList")
    Single<BusSourcesResponse> getBusSourcres();

    @POST("api/pefast.safepe.latepe/api/postBusAvailable")
    Single<BusListResponse> getBusAvailable(@Body BusListRequest busListRequest);

    @POST("api/pefast.safepe.latepe/api/postBusTripDetails")
    Single<BusTripDetailsResponse> getBusTripDetails(@Body BusTripDetailsRequest busTripDetailsRequest);

    @POST("api/pefast.safepe.latepe/api/postBusBlockTicket")
    Single<BusBlockingResponse> getBusBlocking(@Body BusBlockingRequest busBookingRequest);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/getFirebaseTocken")
    Single<BusBookingResponse> getBookingBus(@Field("referenceNo") String referenceNo);

    @GET("api/pefast.safepe.latepe/api/busHistory")
    Single<BusHistoryResponse> getBusHistory();

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/postBusCancelTicket")
    Single<BusCancelResponse> getBusCancel(@Field("referenceNo") String referenceNo, @Field("seatNos") String seatNos);

    @POST("api/pefast.safepe.latepe/api/getBusPdf")
    Single<FlighPdfResponse> getBusPdf(@Body FlightBookingDetailRequest flightBookingDetailRequest);


    //*************Hotel Booking *******************//
    @POST("api/pefast.safepe.latepe/api/hotelSources")
    Single<HotelSourcesResponse> getHotelSources();

    @POST("api/pefast.safepe.latepe/api/hotelAvailable")
    Single<AvailableHotelsResponse> getHotelAvailable(@Body AvailableHotelRequest availableHotelRequest);

    @POST("api/pefast.safepe.latepe/api/hotelDetails")
    Single<HotelDetailResponse> getHotelDetails(@Body HotelDetailsRequest hotelDetailsRequest);

    @POST("api/pefast.safepe.latepe/api/hotelBlock")
    Single<HotelBookResponse> getHotelBook(@Body BookHotelRequest bookHotelRequest);

    @GET("api/pefast.safepe.latepe/api/HotelRoomBookingHistory")
    Single<HotelHistoryResponse> getHotelHistory();

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/hotelCancelRoom")
    Single<HotelCancelResponse> getHotelCancelRoom(@Field("referenceNo") String referenceNo);


    //*************Reward Coin *******************//
    @GET("api/pefast.safepe.latepe/api/coinLog")
    Single<CoinLogResponse> getcoinLog();

    @POST("api/pefast.safepe.latepe/api/saveCoin")
    Single<SaveCoinResponse> getSaveCoin(@Body SaveCoinRequest saveCoinRequest);

    @POST("api/pefast.safepe.latepe/api/redeemCoin")
    Single<RedeemCoinResponse> getRedeemCoin(@Body RedeemCoinRequest redeemCoinRequest);


    //***************Safepe bank investment
    @GET("api/pefast.safepe.latepe/api/getInvestment")
    Single<InvestmentResponse> getInvestmentlist();

    @GET("api/pefast.safepe.latepe/api/safepeInvestmentAccount")
    Single<ResponseModel> safepeInvestmentAccount();

    @GET("api/pefast.safepe.latepe/api/getInvestment")
    Single<ResponseModel> getInvestment();
}
