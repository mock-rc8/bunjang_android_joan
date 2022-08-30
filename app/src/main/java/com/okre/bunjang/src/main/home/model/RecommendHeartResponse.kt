package com.okre.bunjang.src.main.home.model

data class RecommendHeartResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: RecommendHeartResult
)

data class RecommendHeartResult(
    val productIdx: Int,
    val successMessage: String
)