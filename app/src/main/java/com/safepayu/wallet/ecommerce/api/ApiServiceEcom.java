package com.safepayu.wallet.ecommerce.api;

import com.safepayu.wallet.ecommerce.model.request.AddToCartRequest;
import com.safepayu.wallet.ecommerce.model.request.FilterRequest;
import com.safepayu.wallet.ecommerce.model.request.ProductByModifierRequest;
import com.safepayu.wallet.ecommerce.model.request.ProductDetailRequest;
import com.safepayu.wallet.ecommerce.model.response.AddToCartResponse;
import com.safepayu.wallet.ecommerce.model.response.CartListResonse;
import com.safepayu.wallet.ecommerce.model.response.CategoriesResponse;
import com.safepayu.wallet.ecommerce.model.response.HomeCatResponse;
import com.safepayu.wallet.ecommerce.model.response.ParentCategoriesResponse;
import com.safepayu.wallet.ecommerce.model.response.ProductByModifierResponse;
import com.safepayu.wallet.ecommerce.model.response.ProductsByCategoryIdResponse;
import com.safepayu.wallet.ecommerce.model.response.ProductsDetailsResponse;
import com.safepayu.wallet.ecommerce.model.response.VenueDetailsResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServiceEcom {

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/venueDetails")
    Single<VenueDetailsResponse> getVenueDetails(@Field("venue_id") String venue_id);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/getProductBySearch")
    Single<ProductsByCategoryIdResponse> getProductBySearch(@Field("search") String search);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/getProductsByCategoryId")
    Single<ProductsByCategoryIdResponse> getProductsByCategoryId(@Field("search_cat") String CategoryId);


    @POST("api/pefast.safepe.latepe/api/productDetails")
    Single<ProductsDetailsResponse> getProductDetails(@Body ProductDetailRequest productDetailRequest);

    @POST("api/pefast.safepe.latepe/api/productDetailsByModifier")
    Single<ProductByModifierResponse> getProductDetailsByModifier(@Body ProductByModifierRequest productByModifierRequest);

    @POST("api/pefast.safepe.latepe/api/addCarts")
    Single<AddToCartResponse> getAddToCarts(@Body AddToCartRequest addToCartRequest);

    @GET("api/pefast.safepe.latepe/api/getTotalCarts")
    Single<CartListResonse> getCartList();

    @GET("api/pefast.safepe.latepe/api/getAllCategories")
    Single<CategoriesResponse> getAllCategories();

    @GET("api/pefast.safepe.latepe/api/getAllParentCategory")
    Single<ParentCategoriesResponse> getAllParentCategory();

    @GET("api/pefast.safepe.latepe/api/mainHomePage")
    Single<HomeCatResponse> getAllCategoryHome();

    @POST("api/pefast.safepe.latepe/api/searchProductByRefine")
    Single<ProductsByCategoryIdResponse> getProductsFilter(@Body FilterRequest filterRequest);
}
