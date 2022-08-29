package com.okre.bunjang.src.main.home.buy

import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryResponse

interface BuyDeliveryInterface {

    fun onGetBuyDeliverySuccess(response: BuyDeliveryResponse)

    fun onGetBuyDeliveryFailure(message: String)
}