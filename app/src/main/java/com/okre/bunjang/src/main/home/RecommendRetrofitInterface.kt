package com.okre.bunjang.src.main.home

import com.okre.bunjang.src.main.home.model.ProductDetailResponse
import com.okre.bunjang.src.main.home.model.RecommendHeartRequest
import com.okre.bunjang.src.main.home.model.RecommendHeartResponse
import com.okre.bunjang.src.main.home.model.RecommendResponse
import retrofit2.Call
import retrofit2.http.*

interface RecommendRetrofitInterface {
    @GET("/bunjang/products")
    fun getRecommend(): Call<RecommendResponse>

    @GET("bunjang/products/detail/productIdx/{productIdx}")
    fun getProductDetail(
        @Path(value = "productIdx", encoded = true)
        productIdx : Int
    ) : Call<ProductDetailResponse>

    @POST("/bunjang/users/heart")
    fun postHeart(
        @Body
        params: RecommendHeartRequest
    ) : Call<RecommendHeartResponse>

    @PATCH("/bunjang/users/heart")
    fun patchHeart(
        @Body
        params: RecommendHeartRequest
    ) : Call<RecommendHeartResponse>
}