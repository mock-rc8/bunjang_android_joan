package com.okre.bunjang.src.main.home.buy

import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BuyDeliveryRetrofitInterface {
    @GET("/bunjang/products/payments/productIdx/{productIdx}")
    fun getBuyDelivery(
        @Path(value = "productIdx", encoded = true)
        productIdx : Int
    ) : Call<BuyDeliveryResponse>

}