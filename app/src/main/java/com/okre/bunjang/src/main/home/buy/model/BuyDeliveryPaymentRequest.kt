package com.okre.bunjang.src.main.home.buy.model

data class BuyDeliveryPaymentRequest(
    val commissionPrice: Int,
    val finalPrice: Int,
    val paymentMethod: String,
    val productIdx: Int,
    val requirement: String,
    val transactionMethod: String,
    val usePoint: Int,
    val userIdx: Int
)