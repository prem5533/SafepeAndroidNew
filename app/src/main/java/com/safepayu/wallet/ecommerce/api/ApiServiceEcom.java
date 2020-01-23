package com.safepayu.wallet.ecommerce.api;

import com.safepayu.wallet.ecommerce.model.request.AddToCartRequest;
import com.safepayu.wallet.ecommerce.model.request.CartQuantityRequest;
import com.safepayu.wallet.ecommerce.model.request.FilterRequest;
import com.safepayu.wallet.ecommerce.model.request.ProductByModifierRequest;
import com.safepayu.wallet.ecommerce.model.request.ProductDetailRequest;
import com.safepayu.wallet.ecommerce.model.request.WishListRequest;
import com.safepayu.wallet.ecommerce.model.response.AddToCartResponse;
import com.safepayu.wallet.ecommerce.model.response.CartListResonse;
import com.safepayu.wallet.ecommerce.model.response.CartQuantityResponse;
import com.safepayu.wallet.ecommerce.model.response.CategoriesResponse;
import com.safepayu.wallet.ecommerce.model.response.DeleteCartResponse;
import com.safepayu.wallet.ecommerce.model.response.HomeCatResponse;
import com.safepayu.wallet.ecommerce.model.response.MoveToCartResponse;
import com.safepayu.wallet.ecommerce.model.response.ParentCategoriesResponse;
import com.safepayu.wallet.ecommerce.model.response.ProductByModifierResponse;
import com.safepayu.wallet.ecommerce.model.response.ProductsByCategoryIdResponse;
import com.safepayu.wallet.ecommerce.model.response.ProductsDetailsResponse;
import com.safepayu.wallet.ecommerce.model.response.TotalCartResponse;
import com.safepayu.wallet.ecommerce.model.response.VenueDetailsResponse;
import com.safepayu.wallet.ecommerce.model.response.WishListResponse;

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

    @GET("api/pefast.safepe.latepe/api/getTotalCarts")
    Single<TotalCartResponse> getTotalCarts();

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/deleteCart")
    Single<DeleteCartResponse> getDeleteCart(@Field("cart_id") String cart_id);

    ///**********WishList********

    @GET("api/pefast.safepe.latepe/api/getLikeProducts ")
    Single<WishListResponse> getLikeProducts ();

    @POST("api/pefast.safepe.latepe/api/LikeDislikeProduct ")
    Single<WishListResponse> getWishListLikeDislike(@Body WishListRequest wishListRequest);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/moveLikeToCart")
    Single<MoveToCartResponse> getMoveToCart(@Field("like_id") String like_id);

    @FormUrlEncoded
    @POST("api/pefast.safepe.latepe/api/moveCartToLikes")
    Single<MoveToCartResponse> getMoveToWishList(@Field("cart_id") String cart_id);

    @POST("api/pefast.safepe.latepe/api/addCartQuantity")
    Single<CartQuantityResponse> getCartQuantity(@Body CartQuantityRequest cartQuantityRequest);
}
