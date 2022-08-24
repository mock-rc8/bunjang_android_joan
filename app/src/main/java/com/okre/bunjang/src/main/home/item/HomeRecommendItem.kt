package com.okre.bunjang.src.main.home.item
data class HomeRecommendItem (
    var recommendImage : String? = "",
    var recommendCheckbox : Boolean = false,
    var recommendPrice : String? = "",
    var recommendProductName : String? = "",
    var recommendLocation : String? = "",
    var recommendTime : String? = "",
    var recommendLightningPay : Boolean = false,
    var recommendHeartCount : Int? = 0
)