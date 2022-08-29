package com.okre.bunjang.src.main.home.buy

import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class BuyDeliveryService(val buyDeliveryInterface: BuyDeliveryInterface) {

    val buyDeliveryRetrofitInterface = ApplicationClass.sRetrofit.create(BuyDeliveryRetrofitInterface::class.java)

    fun tryGetBuyDelivery(productIdx : Int) {
        buyDeliveryRetrofitInterface.getBuyDelivery(productIdx).enqueue(object : Callback<BuyDeliveryResponse> {
            override fun onResponse(
                call: Call<BuyDeliveryResponse>,
                response: Response<BuyDeliveryResponse>
            ) {
                buyDeliveryInterface.onGetBuyDeliverySuccess(response.body() as BuyDeliveryResponse)
            }

            override fun onFailure(call: Call<BuyDeliveryResponse>, t: Throwable) {
                buyDeliveryInterface.onGetBuyDeliveryFailure(t.message ?: "통신 오류")
            }
        })
    }
}