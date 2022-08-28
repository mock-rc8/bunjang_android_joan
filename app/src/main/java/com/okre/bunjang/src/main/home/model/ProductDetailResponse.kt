package com.okre.bunjang.src.main.home.model

import com.okre.bunjang.config.BaseResponse

data class ProductDetailResponse(
    val result: ProductDetailResult
) : BaseResponse()

data class ProductDetailResult(
    val address: String, // productDetailLocation
    val created: String, // productDetailTime
    val exchange: String, // productDetailOptionExchange
    val hashtag: List<String>,
    val heart: Int, // productDetailHeart
    val pay: Int, // productDetailImgLightningPay 0 false 1 true
    val price: Int, // productDetailTextviewPrice
    val productExplaination: String, // productDetailContent
    val productIdx: Int,
    val productImgURL: String, // viewpager image main
    val productImgURLlist: List<String>, // viewpager image all
    val productName: String, // productDetailTitle
    val quantity: Int, // productDetailOptionCount
    val shippingFee: String, // productDetailOptionDelivery
    val status: String, // productDetailOptionNewOrOld
    val subImgURL: List<Any>, // viewpager image sub
    val talk: Int, // productDetailTalk
    val views: Int // productDetailSee
)