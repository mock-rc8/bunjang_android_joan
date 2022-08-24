package com.okre.bunjang.config

import com.okre.bunjang.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.okre.bunjang.config.ApplicationClass.Companion.loginSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

// 서버 jwt 유저 식별, 자동으로 http통신이 나갈 때 해더에 sp에 저장된 jwt값을 붙여줌
class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = loginSharedPreferences.getString(X_ACCESS_TOKEN, null)
        if (jwtToken != null) {
            builder.addHeader("X-ACCESS-TOKEN", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}