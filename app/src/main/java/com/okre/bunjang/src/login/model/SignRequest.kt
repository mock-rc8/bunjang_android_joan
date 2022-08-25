package com.okre.bunjang.src.login.model

data class SignRequest(
    val carrier: String = "",
    val name: String = "",
    val password: String = "",
    val phoneNum: String = "",
    val residentNumFirst: String = "",
    val residentNumLast: String = "",
    val storeName: String = ""
)