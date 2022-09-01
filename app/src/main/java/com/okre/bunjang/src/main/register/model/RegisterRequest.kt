package com.okre.bunjang.src.main.register.model

data class RegisterRequest(
    val address: String,
    val exchange: String,
    val hashtagText: String,
    val imageURL: String,
    val mainCategory: String,
    val pay: Int,
    val price: Int,
    val productExplaination: String,
    val productStatus: String,
    val productsName: String,
    val quantity: Int,
    val shippingFee: String,
    val subCategory: String,
    val subImageURL: List<String>,
    val userIdx: Int
)