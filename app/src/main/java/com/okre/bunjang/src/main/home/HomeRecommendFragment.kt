package com.okre.bunjang.src.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseFragment
import com.okre.bunjang.databinding.FragmentHomeRecommendBinding
import com.okre.bunjang.src.login.LoginService
import com.okre.bunjang.src.login.adpater.LoginPhoneTelecomAdapter
import com.okre.bunjang.src.login.model.LoginRequest
import com.okre.bunjang.src.main.home.adapter.HomeRecommendAdapter
import com.okre.bunjang.src.main.home.item.HomeRecommendItem
import com.okre.bunjang.src.main.home.model.ProductDetailResponse
import com.okre.bunjang.src.main.home.model.RecommendHeartRequest
import com.okre.bunjang.src.main.home.model.RecommendHeartResponse
import com.okre.bunjang.src.main.home.model.RecommendResponse
import java.text.DecimalFormat

class HomeRecommendFragment : BaseFragment<FragmentHomeRecommendBinding>(FragmentHomeRecommendBinding::bind, R.layout.fragment_home_recommend),
    RecommendFragmentInterface {

    private val rvAdapter = HomeRecommendAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 상품 목록 받아오기
        showLoadingDialog(requireContext())
        RecommendService(this).tryGetRecommend()

        // recyclerview 생성
        recommendRecyclerView()

        heartClick()

    }

    fun recommendRecyclerView() {

        binding.homeRvRecommend.adapter = rvAdapter

    }

    override fun onGetRecommendSuccess(response: RecommendResponse) {
        for (recommendList in response.result) {
            val recommendIdx = recommendList.productIdx
            val recommendImage = recommendList.productURL
            val recommendCheckbox = when(recommendList.userHeart) {
                0 -> false
                else -> true
            }
            val recommendPrice  = getString(R.string.product_detail_price, DecimalFormat("#,###").format(recommendList.price))
            val recommendProductName = recommendList.productName
            val recommendLocation = when (recommendList.address) {
                null -> getString(R.string.product_detail_location)
                "" -> getString(R.string.product_detail_location)
                else -> recommendList.address
            }
            val recommendTime = recommendList.created
            val recommendLightningPay = when(recommendList.pay) {
                0 -> false
                else -> true
            }
            val recommendHeartCount = recommendList.heartCount

            rvAdapter.addList(HomeRecommendItem(recommendIdx, recommendImage, recommendCheckbox, recommendPrice, recommendProductName, recommendLocation, recommendTime, recommendLightningPay, recommendHeartCount))
        }
        dismissLoadingDialog()
    }

    override fun onGetRecommendFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetProductDetailSuccess(response: ProductDetailResponse) {}
    override fun onGetProductDetailFailure(message: String) {}

    fun heartClick() {
        rvAdapter.itemClick = object : HomeRecommendAdapter.ItemClick {
            override fun onClick(view: View, position: Int, productIdx: Int, checkbox: Boolean) {
                if (checkbox) {
                    showLoadingDialog(requireContext())
                    val recommendHeartRequest = RecommendHeartRequest(productIdx)
                    RecommendService(this@HomeRecommendFragment).tryPostHeart(recommendHeartRequest)
                } else {
                    showLoadingDialog(requireContext())
                    val recommendHeartRequest = RecommendHeartRequest(productIdx)
                    RecommendService(this@HomeRecommendFragment).tryPatchHeart(recommendHeartRequest)
                }

            }

        }
    }
    override fun onPostHeartSuccess(response: RecommendHeartResponse) {
        // 화면에 하트 +1
        //RecommendService(this).tryGetRecommend() -> ?
        showCustomToast(response.result.successMessage)
        dismissLoadingDialog()
    }

    override fun onPostHeartDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPatchHeartSuccess(response: RecommendHeartResponse) {
        // 화면에 하트 +1
        //RecommendService(this).tryGetRecommend() -> ?
        showCustomToast(response.result.successMessage)
        dismissLoadingDialog()
    }

    override fun onPatchHeartDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}