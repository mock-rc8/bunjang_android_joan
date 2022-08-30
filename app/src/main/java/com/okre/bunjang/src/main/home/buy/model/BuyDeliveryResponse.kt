package com.okre.bunjang.src.main.home.buy.model

import com.okre.bunjang.config.BaseResponse

data class BuyDeliveryResponse(
    val result: BuyDeliveryResult
) : BaseResponse()

data class BuyDeliveryResult(
    val userIdx: Int,
    val address: String,
    val commission: String,
    val detailAddress: String,
    val point: Int,
    val productImgURL: String,
    val productName: String,
    val productPrice: Int,
    val shippingFee: String
)