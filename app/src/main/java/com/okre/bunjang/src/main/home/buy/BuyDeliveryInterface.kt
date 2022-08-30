package com.okre.bunjang.src.main.home.buy

import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryAddressAddResponse
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryAddressManageResponse
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryResponse

interface BuyDeliveryInterface {

    fun onGetBuyDeliverySuccess(response: BuyDeliveryResponse)

    fun onGetBuyDeliveryFailure(message: String)

    fun onGetUserShippingSuccess(response: BuyDeliveryAddressManageResponse)

    fun onGetUserShippingFailure(message: String)

    fun onPostUserShippingSuccess(response: BuyDeliveryAddressAddResponse)

    fun onPostUserShippingFailure(message: String)

}