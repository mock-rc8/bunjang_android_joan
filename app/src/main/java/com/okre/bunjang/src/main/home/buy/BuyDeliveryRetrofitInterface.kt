package com.okre.bunjang.src.main.home.buy

import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryAddressAddRequest
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryAddressAddResponse
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryAddressManageResponse
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BuyDeliveryRetrofitInterface {
    @GET("/bunjang/products/payments/productIdx/{productIdx}")
    fun getBuyDelivery(
        @Path(value = "productIdx", encoded = true)
        productIdx : Int
    ) : Call<BuyDeliveryResponse>

    @GET("/bunjang/users/shipping")
    fun getUserShipping() : Call<BuyDeliveryAddressManageResponse>

    @POST("/bunjang/users/shipping")
    fun postUserShipping(
        @Body
        params : BuyDeliveryAddressAddRequest
    ) : Call<BuyDeliveryAddressAddResponse>
}