package com.okre.bunjang.src.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityHomeRecommendProductDetailBinding
import com.okre.bunjang.src.main.home.adapter.ProductDetailImageViewPagerAdapter
import com.okre.bunjang.src.main.home.buy.BuyDeliveryActivity
import com.okre.bunjang.src.main.home.model.ProductDetailResponse
import com.okre.bunjang.src.main.home.model.RecommendResponse
import java.text.DecimalFormat

class HomeRecommendProductDetailActivity : BaseActivity<ActivityHomeRecommendProductDetailBinding>(ActivityHomeRecommendProductDetailBinding::inflate),
    RecommendFragmentInterface {

    var detailImage = mutableListOf<String>()
    private lateinit var safePayBottomSheetDialog : BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 상세 페이지 받아오기
        showLoadingDialog(this)
        val productIdx = intent.getIntExtra("productIdx", 0)
        RecommendService(this).tryGetProductDetail(productIdx)

        // 안전하게 결제하기 버튼 클릭
        safePayDialog()

        scrollDetailTopShow()

    }

    fun scrollDetailTopShow() {
        //binding.productDetailTopLayout.y
        //binding.productDetailTopLayout.animate().translationY(-binding.productDetailTopLayout.height.toFloat())

        binding.productDetailScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > 1260) {
                binding.productDetailTopLayout.animate().translationY(binding.productDetailTopLayout.height.toFloat())
            } else {
                binding.productDetailTopLayout.animate().translationY(0f)
            }
            Log.d("dddd", scrollY.toString())
            //Log.d("ddd", oldScrollY.toString())
        }
    }

    fun detailImageViewPager() {
        binding.productDetailViewpager.adapter = ProductDetailImageViewPagerAdapter(detailImage)
        if (detailImage.size == 1) {
            binding.productDetailViewpagerCount.visibility = View.GONE
        } else {
            binding.productDetailViewpagerCount.text =
                getString(R.string.home_viewpager_count, 1, detailImage.size)
        }

        binding.productDetailViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.productDetailViewpagerCount.text = getString(R.string.home_viewpager_count, position + 1, detailImage.size)
            }
        })
    }

    fun safePayDialog() {
        val safePayBottomSheetView = layoutInflater.inflate(R.layout.dialog_product_detail_safe_pay, null)
        safePayBottomSheetDialog = BottomSheetDialog(this)
        safePayBottomSheetDialog.setContentView(safePayBottomSheetView)

        binding.productDetailBtnSafePay.setOnClickListener {
            safePayBottomSheetDialog.show()
        }

        val deliveryBtn = safePayBottomSheetView.findViewById<ConstraintLayout>(R.id.dialog_safe_pay_delivery_layout)
        val deliveryCheckbox = safePayBottomSheetView.findViewById<CheckBox>(R.id.dialog_safe_pay_delivery_checkbox)
        val directBtn = safePayBottomSheetView.findViewById<ConstraintLayout>(R.id.dialog_safe_pay_direct_layout)
        val directCheckbox = safePayBottomSheetView.findViewById<CheckBox>(R.id.dialog_safe_pay_direct_checkbox)
        val nextBtn = safePayBottomSheetView.findViewById<Button>(R.id.product_detail_btn_safe_pay)

        deliveryBtn.setOnClickListener {
            deliveryCheckbox.isChecked = true
            deliveryBtn.setBackgroundResource(R.drawable.background_btn_safe_pay_check)
            directCheckbox.isChecked = false
            directBtn.setBackgroundResource(R.drawable.background_btn_product_detail_event)
        }

        directBtn.setOnClickListener {
            directCheckbox.isChecked = true
            directBtn.setBackgroundResource(R.drawable.background_btn_safe_pay_check)
            deliveryCheckbox.isChecked = false
            deliveryBtn.setBackgroundResource(R.drawable.background_btn_product_detail_event)
        }

        nextBtn.setOnClickListener {
            safePayBottomSheetDialog.dismiss()
            if (deliveryCheckbox.isChecked) {
                val intent = Intent(this, BuyDeliveryActivity::class.java)
                intent.putExtra("selectDeliveryOrDirect", "Delivery")
                startActivity(intent)
            } else {
                val intent = Intent(this, BuyDeliveryActivity::class.java)
                intent.putExtra("selectDeliveryOrDirect", "Direct")
                startActivity(intent)
            }
        }
    }

    override fun onGetRecommendSuccess(response: RecommendResponse) {}
    override fun onGetRecommendFailure(message: String) {}

    override fun onGetProductDetailSuccess(response: ProductDetailResponse) {

        detailImage.add(response.result.productImgURL)
        Glide.with(this)
            .load(response.result.productImgURL)
            .into(binding.productDetailTopImg)
        // 이미지 뷰페이저
        detailImageViewPager()
        binding.productDetailTextviewPrice.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(response.result.price))
        binding.productDetailTopPrice.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(response.result.price))
        if (response.result.pay == 0) {
            binding.productDetailImgLightningPay.visibility = View.GONE
            binding.productDetailTopLightningPay.visibility = View.GONE
        }
        binding.productDetailTitle.text = response.result.productName
        binding.productDetailTopTitle.text = response.result.productName
        binding.productDetailLocation.text = when (response.result.address) {
            null -> getString(R.string.product_detail_location)
            "" -> getString(R.string.product_detail_location)
            else -> response.result.address
        }
        binding.productDetailTime.text = response.result.created
        binding.productDetailTalk.text = response.result.talk.toString()
        binding.productDetailHeart.text = response.result.heart.toString()
        binding.productDetailSee.text = response.result.views.toString()
        binding.productDetailOptionNewOrOld.text = response.result.status
        binding.productDetailOptionCount.text = getString(R.string.product_detail_option_count, response.result.quantity.toString())
        binding.productDetailOptionDelivery.text = response.result.shippingFee
        binding.productDetailOptionExchange.text = response.result.exchange
        binding.productDetailContent.text = response.result.productExplaination

        when (response.result.hashtag.size) {
            0 -> null
            1 -> {binding.productDetailTag1.visibility = View.VISIBLE
                binding.productDetailTag1.text = getString(R.string.product_detail_tag, response.result.hashtag[0]) }
            2 -> {binding.productDetailTag1.visibility = View.VISIBLE
                binding.productDetailTag2.visibility = View.VISIBLE
                binding.productDetailTag1.text = getString(R.string.product_detail_tag, response.result.hashtag[0])
                binding.productDetailTag2.text = getString(R.string.product_detail_tag, response.result.hashtag[1]) }
            3 -> {binding.productDetailTag1.visibility = View.VISIBLE
                binding.productDetailTag2.visibility = View.VISIBLE
                binding.productDetailTag3.visibility = View.VISIBLE
                binding.productDetailTag1.text = getString(R.string.product_detail_tag, response.result.hashtag[0])
                binding.productDetailTag2.text = getString(R.string.product_detail_tag, response.result.hashtag[1])
                binding.productDetailTag3.text = getString(R.string.product_detail_tag, response.result.hashtag[2])}
            4 -> {binding.productDetailTag1.visibility = View.VISIBLE
                binding.productDetailTag2.visibility = View.VISIBLE
                binding.productDetailTag3.visibility = View.VISIBLE
                binding.productDetailTag4.visibility = View.VISIBLE
                binding.productDetailTag1.text = getString(R.string.product_detail_tag, response.result.hashtag[0])
                binding.productDetailTag2.text = getString(R.string.product_detail_tag, response.result.hashtag[1])
                binding.productDetailTag3.text = getString(R.string.product_detail_tag, response.result.hashtag[2])
                binding.productDetailTag4.text = getString(R.string.product_detail_tag, response.result.hashtag[3])}
            5 -> {binding.productDetailTag1.visibility = View.VISIBLE
                binding.productDetailTag2.visibility = View.VISIBLE
                binding.productDetailTag3.visibility = View.VISIBLE
                binding.productDetailTag4.visibility = View.VISIBLE
                binding.productDetailTag5.visibility = View.VISIBLE
                binding.productDetailTag1.text = getString(R.string.product_detail_tag, response.result.hashtag[0])
                binding.productDetailTag2.text = getString(R.string.product_detail_tag, response.result.hashtag[1])
                binding.productDetailTag3.text = getString(R.string.product_detail_tag, response.result.hashtag[2])
                binding.productDetailTag4.text = getString(R.string.product_detail_tag, response.result.hashtag[3])
                binding.productDetailTag5.text = getString(R.string.product_detail_tag, response.result.hashtag[4])}
        }


        dismissLoadingDialog()
    }

    override fun onGetProductDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

}