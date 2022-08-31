package com.okre.bunjang.src.main.register.model

data class SubCategoryResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<SubCategoryResult>
)

data class SubCategoryResult(
    val categoryName: String,
    val subCategoryIdx: Int
)