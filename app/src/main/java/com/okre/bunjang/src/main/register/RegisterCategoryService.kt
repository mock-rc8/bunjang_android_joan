package com.okre.bunjang.src.main.register

import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.src.main.register.model.MainCategoryResponse
import com.okre.bunjang.src.main.register.model.SubCategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class RegisterCategoryService(val registerCategoryInterface: RegisterCategoryInterface) {

    val registerCategoryRetrofitInterface = ApplicationClass.sRetrofit.create(RegisterCategoryRetrofitInterface::class.java)

    fun tryGetMain() {
        registerCategoryRetrofitInterface.getMain().enqueue(object : Callback<MainCategoryResponse> {
            override fun onResponse(
                call: Call<MainCategoryResponse>,
                response: Response<MainCategoryResponse>
            ) {
                registerCategoryInterface.onGetMainSuccess(response.body() as MainCategoryResponse)
            }

            override fun onFailure(call: Call<MainCategoryResponse>, t: Throwable) {
                registerCategoryInterface.onGetMainFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetSub(mainCategoryIdx : Int) {
        registerCategoryRetrofitInterface.getSub(mainCategoryIdx).enqueue(object : Callback<SubCategoryResponse> {
            override fun onResponse(
                call: Call<SubCategoryResponse>,
                response: Response<SubCategoryResponse>
            ) {
                registerCategoryInterface.onGetSubSuccess(response.body() as SubCategoryResponse)
            }

            override fun onFailure(call: Call<SubCategoryResponse>, t: Throwable) {
                registerCategoryInterface.onGetSubFailure(t.message ?: "통신 오류")
            }
        })

    }
}