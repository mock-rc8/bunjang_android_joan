package com.okre.bunjang.src.main

data class UserInfoResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: UserInfoResult
)

data class UserInfoResult(
    val storeName: String
)