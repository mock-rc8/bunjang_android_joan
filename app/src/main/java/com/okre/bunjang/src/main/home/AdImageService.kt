package com.okre.bunjang.src.main.home

import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.src.main.home.model.AdImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class AdImageService(val adImageInterface: AdImageInterface) {

    val adImageRetrofitInterface = ApplicationClass.sRetrofit.create(AdImageRetrofitInterface::class.java)

    fun tryGetAdImage() {
        adImageRetrofitInterface.getAdImage().enqueue(object : Callback<AdImageResponse> {
            override fun onResponse(
                call: Call<AdImageResponse>,
                response: Response<AdImageResponse>
            ) {
                adImageInterface.onGetAdImageSuccess(response.body() as AdImageResponse)
            }

            override fun onFailure(call: Call<AdImageResponse>, t: Throwable) {
                adImageInterface.onGetAdImageFailure(t.message ?: "통신 오류")
            }

        })
    }
}