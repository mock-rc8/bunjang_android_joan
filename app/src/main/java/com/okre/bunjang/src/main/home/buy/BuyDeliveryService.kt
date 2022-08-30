package com.okre.bunjang.src.main.home.buy

import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.src.main.home.buy.model.*
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

    fun tryGetUserShipping() {
        buyDeliveryRetrofitInterface.getUserShipping().enqueue(object : Callback<BuyDeliveryAddressManageResponse> {
            override fun onResponse(
                call: Call<BuyDeliveryAddressManageResponse>,
                response: Response<BuyDeliveryAddressManageResponse>
            ) {
                buyDeliveryInterface.onGetUserShippingSuccess(response.body() as BuyDeliveryAddressManageResponse)
            }

            override fun onFailure(call: Call<BuyDeliveryAddressManageResponse>, t: Throwable) {
                buyDeliveryInterface.onGetUserShippingFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPosstUserShipping(buyDeliveryAddressAddRequest: BuyDeliveryAddressAddRequest) {
        buyDeliveryRetrofitInterface.postUserShipping(buyDeliveryAddressAddRequest).enqueue(object : Callback<BuyDeliveryAddressAddResponse> {
            override fun onResponse(
                call: Call<BuyDeliveryAddressAddResponse>,
                response: Response<BuyDeliveryAddressAddResponse>
            ) {
                buyDeliveryInterface.onPostUserShippingSuccess(response.body() as BuyDeliveryAddressAddResponse)
            }

            override fun onFailure(call: Call<BuyDeliveryAddressAddResponse>, t: Throwable) {
                buyDeliveryInterface.onPostUserShippingFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPostBuyDeliveryPayment(buyDeliveryPaymentRequest: BuyDeliveryPaymentRequest) {
        buyDeliveryRetrofitInterface.postBuyDeliveryPayment(buyDeliveryPaymentRequest).enqueue(object : Callback<BuyDeliveryPaymentResponse> {
            override fun onResponse(
                call: Call<BuyDeliveryPaymentResponse>,
                response: Response<BuyDeliveryPaymentResponse>
            ) {
                buyDeliveryInterface.onPostBuyDeliveryPaymentSuccess(response.body() as BuyDeliveryPaymentResponse)
            }

            override fun onFailure(call: Call<BuyDeliveryPaymentResponse>, t: Throwable) {
                buyDeliveryInterface.onPostBuyDeliveryPaymentFailure(t.message ?: "통신 오류")
            }

        })
    }
}