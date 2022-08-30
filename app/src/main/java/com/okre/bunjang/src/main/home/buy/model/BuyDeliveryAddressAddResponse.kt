package com.okre.bunjang.src.main.home.buy.model

import com.okre.bunjang.config.BaseResponse

data class BuyDeliveryAddressAddResponse(
    val result: BuyDeliveryAddressAddResult
) : BaseResponse()

data class BuyDeliveryAddressAddResult(
    val address: String,
    val detailAddress: String,
    val receiverName: String,
    val receiverPhoneNum: String,
    val shippingIdx: Int,
    val status: String
)