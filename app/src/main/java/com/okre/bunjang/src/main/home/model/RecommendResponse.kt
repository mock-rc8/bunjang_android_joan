package com.okre.bunjang.src.main.home.model

import com.okre.bunjang.config.BaseResponse

data class RecommendResponse(
    val result: List<ResultRecommend>
) : BaseResponse()

data class ResultRecommend(
    val productIdx : Int, //recommendIdx
    val address: String, //recommendLocation
    val created: String, //recommendTime
    val heartCount: Int, //recommendHeartCount
    val pay: Int, //recommendLightningPay 0 : false 1 : true
    val price: Int, // recommendPrice
    val productName: String, //recommendProductName
    val productURL: String, // recommendImage
    val userHeart: Int // recommendCheckbox 0 : false 1 : true
)
