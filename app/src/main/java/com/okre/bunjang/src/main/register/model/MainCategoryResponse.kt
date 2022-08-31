package com.okre.bunjang.src.main.register.model

data class MainCategoryResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<MainCategoryResult>
)

data class MainCategoryResult(
    val categoryName: String,
    val mainCategoryIdx: Int
)