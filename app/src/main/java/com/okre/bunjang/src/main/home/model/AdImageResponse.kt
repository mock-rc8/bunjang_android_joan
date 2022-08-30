package com.okre.bunjang.src.main.home.model

data class AdImageResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: AdImageResult
)

data class AdImageResult(
    val adImageList: List<String>
)