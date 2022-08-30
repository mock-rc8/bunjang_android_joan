package com.okre.bunjang.src.main.home.buy.model

data class BuyDeliveryAddressAddRequest(
    val address: String,
    val detailAddress: String,
    val receiverName: String,
    val receiverPhoneNum: String
)