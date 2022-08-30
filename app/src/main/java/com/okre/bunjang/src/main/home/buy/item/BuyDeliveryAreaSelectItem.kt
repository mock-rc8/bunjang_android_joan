package com.okre.bunjang.src.main.home.buy.item

data class BuyDeliveryAreaSelectItem (
    val address : String = "",
    val detailAddress : String = "",
    val receiverName : String = "",
    val receiverPhoneNum : String = "",
    val shippingIdx: Int,
    val status: String
)