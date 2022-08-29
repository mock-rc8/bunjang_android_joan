package com.okre.bunjang.src.main.home.buy

import android.content.Intent
import android.graphics.Paint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityBuyDeliveryBinding
import com.okre.bunjang.src.main.home.buy.adapter.BuyDeliveryAddressOptionAdapter
import com.okre.bunjang.src.main.home.buy.adapter.BuyDeliveryAreaSelectAdapter
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryAreaSelectItem
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryResponse
import java.text.DecimalFormat

class BuyDeliveryActivity : BaseActivity<ActivityBuyDeliveryBinding>(ActivityBuyDeliveryBinding::inflate),
    BuyDeliveryInterface {

    lateinit var animation : AnimationDrawable
    var areaSelectItem = mutableListOf<BuyDeliveryAreaSelectItem>()
    var optionItem = mutableListOf<String>()

    private var tax : Int = 0 // 수수료
    private var taxFinal : Int = 0 // 3500원 혜택 받은 총 수수료
    private var paymentPrice : Int = 0 // 결제 금액 = 상품 금액 - 수수료
    private var point : Int = 0 // 소장 포인트
    private var pointUse : Int = 0 // 사용포인트
    private var paymentFinalPrice : Int = 0 // 총 결제 금액 = 결제금액 - 사용 포인트

    private var paymentMethod : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 택배거래 or 직거래
        if (intent.getStringExtra("selectDeliveryOrDirect") == "Direct") {
            binding.buyDeliveryOrDirectTitle.text = getString(R.string.buy_direct_title)
            binding.buyDeliveryAreaLayout.visibility = View.GONE
        } else {
            // 택배거래 api
            showLoadingDialog(this)
            val productIdx = intent.getIntExtra("productIdx", 0)
            BuyDeliveryService(this).tryGetBuyDelivery(productIdx)

            // 배송지 등록
            clickAddressRegister()

            // 배송 요청 사항
            clickAddressOption()
        }

        // 코인 이미지 회전
        animation = binding.buyDeliveryPaymentSpinningCoin.background as AnimationDrawable
        animation.start()

        // 취소선
        binding.buyDeliveryPaymentTaxDiscount.paintFlags= binding.buyDeliveryPaymentTaxDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        // 포인트 사용
        pointUse()

        // 결제수단 선택
        paymentMethodSelect()

        // 다른 결제수단 선택
        paymentMethodOtherSelect()

        // 결제하기 버튼 클릭
        paymentBtnClick()

    }

    fun paymentBtnClick() {
        // && 결제수단 선택 안했으면 결제수단을 선택해주세요
        if (binding.buyDeliveryMethodOtherCheckbox.isChecked  ) {

        }
    }

    fun paymentMethodSelect() {
        paymentMethod = "번개장터 간편결제"

        binding.buyDeliveryMethodSimpleLayout.setOnClickListener {
            paymentMethod = "번개장터 간편결제"
            binding.buyDeliveryMethodSimpleCheckbox.isChecked = true
            binding.buyDeliveryMethodSimpleTxt.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.buyDeliveryMethodOtherCheckbox.isChecked = false
            binding.buyDeliveryMethodOtherTxt.setTextColor(ContextCompat.getColor(this, R.color.buy_delivery_method_uncheck))
        }

        binding.buyDeliveryMethodOtherLayout.setOnClickListener {
            binding.buyDeliveryMethodOtherCheckbox.isChecked = true
            binding.buyDeliveryMethodOtherTxt.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.buyDeliveryMethodSimpleCheckbox.isChecked = false
            binding.buyDeliveryMethodSimpleTxt.setTextColor(ContextCompat.getColor(this, R.color.buy_delivery_method_uncheck))
        }

        binding.buyDeliveryMethodOtherSelectLayout.setOnClickListener {
            val intentOtherSelect = Intent(this, BuyDeliveryMethodOtherActivity::class.java)
            startActivity(intentOtherSelect)
        }
    }

    fun paymentMethodOtherSelect() {

    }

    fun pointUse() {
        binding.buyDeliveryPoint.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                var pointWant = p0.toString().toInt()
                Log.d("dddd", pointWant.toString())
                if(p0 != null && !p0.toString().equals("")) {
                    if (point < pointWant) {
                        showCustomToast("사용 가능한 포인트가 없어요")
                    } else {
                        pointUse = pointWant
                        binding.buyDeliveryPaymentPointWon.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(pointUse))
                        paymentFinalPrice = paymentPrice - pointUse
                        binding.buyDeliveryPaymentFinalPriceWon.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(paymentFinalPrice))
                    }
                }
            }
        })
    }

    fun clickAddressRegister() {
        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_buy_delivery_area_register, null)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)

        binding.buyDeliveryAddressRegister.setOnClickListener {
            bottomSheetDialog.show()
        }

        val areaRegisterBtn = bottomSheetView.findViewById<ImageView>(R.id.dialog_area_pencil)
        areaRegisterBtn.setOnClickListener {
            val intent = Intent(this, BuyDeliveryAddressManageActivity::class.java)
            startActivity(intent)
        }

        //배송지 있으면 rv visibility visible 없으면 gone
        val areaRegisterRv = bottomSheetView.findViewById<RecyclerView>(R.id.dialog_area_rv)
        areaRegisterRv.adapter = BuyDeliveryAreaSelectAdapter(areaSelectItem)

    }

    fun clickAddressOption() {
        val optionBottomSheetView = layoutInflater.inflate(R.layout.dialog_buy_delivery_address_options, null)
        val optionBottomSheetDialog = BottomSheetDialog(this)
        optionBottomSheetDialog.setContentView(optionBottomSheetView)

        binding.buyDeliveryAreaDemandLayout.setOnClickListener {
            optionBottomSheetDialog.show()
        }

        optionItem.add("문앞")
        optionItem.add("직접 받고 부재 시 문앞")
        optionItem.add("경비실")
        optionItem.add("우편함")
        optionItem.add("직접입력")

        val optionRv = optionBottomSheetView.findViewById<RecyclerView>(R.id.dialog_address_options_rv)
        optionRv.adapter = BuyDeliveryAddressOptionAdapter(optionItem)

    }

    override fun onDestroy() {
        super.onDestroy()
        animation.stop()
    }

    override fun onGetBuyDeliverySuccess(response: BuyDeliveryResponse) {

        Glide.with(this)
            .load(response.result.productImgURL)
            .into(binding.buyDeliveryImg)
        binding.buyDeliveryPrice.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(response.result.productPrice))
        binding.buyDeliveryTitle.text = response.result.productName

        point = response.result.point
        binding.buyDeliveryPointUsePossible.text = point.toString()

        binding.buyDeliveryPaymentProductPriceWon.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(response.result.productPrice))
        tax = (response.result.productPrice * 0.035).toInt()
        binding.buyDeliveryPaymentTaxDiscount.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(tax))
        if (tax <= 3500) {
            taxFinal = 0
            binding.buyDeliveryPaymentTaxFree.text = "무료"
        } else {
            taxFinal = tax - 3500
            binding.buyDeliveryPaymentTaxFree.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(taxFinal))
        }
        binding.buyDeliveryPaymentDeliveryWon.text = response.result.shippingFee
        paymentPrice = response.result.productPrice - taxFinal
        binding.buyDeliveryPaymentPriceWon.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(paymentPrice))
        binding.buyDeliveryPaymentPointWon.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(pointUse))
        binding.buyDeliveryPaymentFinalPriceWon.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(paymentPrice))

        dismissLoadingDialog()
    }

    override fun onGetBuyDeliveryFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}