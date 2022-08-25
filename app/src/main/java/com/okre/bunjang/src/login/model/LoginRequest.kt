package com.okre.bunjang.src.login.model

data class LoginRequest(
    val carrier: String = "",
    val name: String = "",
    val password: String = "",
    val phoneNum: String = "",
    val residentNumFirst: String = "",
    val residentNumLast: String = ""
)