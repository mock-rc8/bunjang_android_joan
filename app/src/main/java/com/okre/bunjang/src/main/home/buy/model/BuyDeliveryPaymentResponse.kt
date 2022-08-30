package com.okre.bunjang.src.main.home.buy.model

data class BuyDeliveryPaymentResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: BuyDeliveryPaymentResult
)

data class BuyDeliveryPaymentResult(
    val finalPrice: Int,
    val productIdx: Int,
    val productName: String
)