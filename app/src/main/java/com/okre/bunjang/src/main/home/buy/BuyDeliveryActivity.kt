package com.okre.bunjang.src.main.home.buy

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.okre.bunjang.R
import com.okre.bunjang.config.ApplicationClass
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityBuyDeliveryBinding
import com.okre.bunjang.src.main.MainActivity
import com.okre.bunjang.src.main.home.HomeFragment
import com.okre.bunjang.src.main.home.buy.adapter.BuyDeliveryAddressOptionAdapter
import com.okre.bunjang.src.main.home.buy.adapter.BuyDeliveryAreaSelectAdapter
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryAreaSelectItem
import com.okre.bunjang.src.main.home.buy.model.*
import java.text.DecimalFormat

class BuyDeliveryActivity : BaseActivity<ActivityBuyDeliveryBinding>(ActivityBuyDeliveryBinding::inflate),
    BuyDeliveryInterface {

    var productIdx = 0
    lateinit var animation : AnimationDrawable
    var areaSelectItem : List<BuyDeliveryAddressManageResult> = arrayListOf()
    var deliveryAreaSelectAdapter = BuyDeliveryAreaSelectAdapter(areaSelectItem)
    lateinit var areaRegisterRv : RecyclerView
    lateinit var areaRegisterEmpty : LinearLayout
    var optionItem = mutableListOf<String>()

    private var tax : Int = 0 // 수수료
    private var taxFinal : Int = 0 // 3500원 혜택 받은 총 수수료
    private var paymentPrice : Int = 0 // 결제 금액 = 상품 금액 - 수수료
    private var point : Int = 0 // 소장 포인트
    private var pointUse : Int = 0 // 사용포인트
    private var paymentFinalPrice : Int = 0 // 총 결제 금액 = 결제금액 - 사용 포인트

    var registerName : String? = null
    var registerAddress : String? = null
    var registerDetailAddress : String? = null
    var registerPhone : String? = null

    var addressOption : String = ""
    var paymentMethod : String = ""
    var transactionMethod = ""

    var agree : Boolean = false

    lateinit var temporaryShared : SharedPreferences
    lateinit var temporaryeditor: SharedPreferences.Editor
    var temporary = false

    private var userIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 택배거래 or 직거래
        if (intent.getStringExtra("selectDeliveryOrDirect") == "Direct") {
            binding.buyDeliveryOrDirectTitle.text = getString(R.string.buy_direct_title)
            binding.buyDeliveryAreaLayout.visibility = View.GONE
            transactionMethod = "직거래"
        } else {
            transactionMethod = "택배거래"
        }

        // 코인 이미지 회전
        animation = binding.buyDeliveryPaymentSpinningCoin.background as AnimationDrawable
        animation.start()

        // 취소선
        binding.buyDeliveryPaymentTaxDiscount.paintFlags= binding.buyDeliveryPaymentTaxDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        temporaryShared = getSharedPreferences("temporary", MODE_PRIVATE)
        temporaryeditor = temporaryShared.edit()

        // 택배거래 api
        showLoadingDialog(this)
        productIdx = intent.getIntExtra("productIdx", 0)
        BuyDeliveryService(this).tryGetBuyDelivery(productIdx)

        // 배송지 등록
        clickAddressRegister()

        // 배송 요청 사항
        clickAddressOption()

        // 포인트 사용
        pointUse()

        // 결제수단 선택
        paymentMethodSelect()

        // 개인정보 동의
        agreeClick()

        // 결제하기 버튼 클릭
        paymentBtnClick()

    }

    fun agreeClick() {
        binding.buyTxtAgree.setOnClickListener {
            if (agree) {
                binding.buyBtnAgree.setImageResource(R.drawable.icon_terms_accept_all_unchecked)
                agree = false
            } else {
                binding.buyBtnAgree.setImageResource(R.drawable.icon_terms_accept_all_checked)
                agree = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // 배송지 appi
        //showLoadingDialog(this)
        BuyDeliveryService(this).tryGetUserShipping()

        Log.d("pppp2", addressOption.toString())
        addressOption = temporaryShared.getString("addressOption", null).toString()
        Log.d("pppp3", addressOption.toString())
        if (addressOption != "null") {
            binding.buyDeliveryAreaDemand.text = addressOption
            binding.buyDeliveryAreaDemand.setTextColor(ContextCompat.getColor(baseContext, R.color.black))
        }
    }

    override fun onStop() {
        super.onStop()
        if (pointUse != 0) {
            temporaryeditor.putInt("pointUse", pointUse)
            temporaryeditor.apply()
        }
        if (addressOption != null) {
            Log.d("pppp4", addressOption.toString())
            temporaryeditor.putString("addressOption", addressOption)
            temporaryeditor.commit()
        }
    }

    fun paymentBtnClick() {
        binding.buyDeliveryBtnPayment.setOnClickListener {
            if (binding.buyDeliveryMethodOtherCheckbox.isChecked && paymentMethod == "null") {
                showCustomToast("결제수단을 선택해주세요.")
            }
            if (!agree) {
                showCustomToast("결제 이용약관을 동의해주세요.")
            }
            if (areaRegisterEmpty.visibility == View.VISIBLE) {
                showCustomToast("배송지 정보를 입력해주세요.")
            }

            val buyDeliveryPaymentRequest = BuyDeliveryPaymentRequest(taxFinal, paymentFinalPrice, paymentMethod, productIdx,
                addressOption, transactionMethod, pointUse, userIdx)
            showLoadingDialog(this)
            BuyDeliveryService(this).tryPostBuyDeliveryPayment(buyDeliveryPaymentRequest)

        }
    }

    fun paymentMethodSelect() {
        paymentMethod = intent.getStringExtra("paymentMethodOtherSelect").toString()

        if (paymentMethod != "null") {
            binding.buyDeliveryMethodOtherSelectTxt.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.buyDeliveryMethodOtherSelectTxt.text = paymentMethod
            binding.buyDeliveryMethodOtherSelectTxt.setTextSize(Dimension.SP, 19F)
            binding.buyDeliveryMethodOtherSelectTxt.setTypeface(binding.buyDeliveryMethodOtherSelectTxt.typeface, Typeface.BOLD)
            binding.buyDeliveryMethodOtherSelectBtn.text = "변경"
        }

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
            intentOtherSelect.putExtra("productIdx", productIdx)
            startActivity(intentOtherSelect)
        }
    }

    fun pointUse() {
        binding.buyDeliveryPoint.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if(p0 != null && !p0.toString().equals("")) {
                    var pointWant = p0.toString().toInt()
                    if (point < pointWant) {
                        if (!temporary) {
                            showCustomToast("사용 가능한 포인트가 없어요")
                        }
                    } else {
                        pointUse = pointWant
                        binding.buyDeliveryPaymentPointWon.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(pointUse))
                        pointCalculate()
                        Log.d("pppp", pointUse.toString())
                    }
                } else {
                    binding.buyDeliveryPoint.setText("0")
                    pointUse = 0
                    Log.d("pppp", pointUse.toString())
                }
            }
        })
    }

    fun pointCalculate() {
        paymentFinalPrice = paymentPrice - pointUse
        binding.buyDeliveryPaymentFinalPriceWon.text = getString(R.string.product_detail_price, DecimalFormat("#,###").format(paymentFinalPrice))
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
            bottomSheetDialog.dismiss()
        }

        //배송지 있으면 rv visibility visible 없으면 gone
        areaRegisterRv = bottomSheetView.findViewById<RecyclerView>(R.id.dialog_area_rv)

        areaRegisterEmpty = bottomSheetView.findViewById(R.id.dialog_area_no)

        deliveryAreaSelectAdapter.itemClick = object : BuyDeliveryAreaSelectAdapter.ItemClick {
            override fun onClick(
                view: View,
                position: Int,
                address: String,
                detailAddress: String,
                name: String,
                phone: String
            ) {
                registerAddress = address
                registerDetailAddress = detailAddress
                registerName = name
                registerPhone = phone
                areaRegister()
                bottomSheetDialog.dismiss()
            }
        }
    }

    fun areaRegister() {
        binding.buyDeliveryAreaRegisterAddress.text = registerAddress + " " + registerDetailAddress
        binding.buyDeliveryAreaRegisterName.text = registerName
        binding.buyDeliveryAreaRegisterPhone.text = registerPhone
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
        val optionAdapter = BuyDeliveryAddressOptionAdapter(optionItem)
        optionRv.adapter = optionAdapter

        optionAdapter.itemClick = object : BuyDeliveryAddressOptionAdapter.ItemClick {
            override fun onClick(view: View, position: Int, text: String) {
                binding.buyDeliveryAreaDemand.text = text
                binding.buyDeliveryAreaDemand.setTextColor(ContextCompat.getColor(baseContext, R.color.black))
                addressOption = text
                optionBottomSheetDialog.dismiss()
            }
        }
    }

    override fun onGetBuyDeliverySuccess(response: BuyDeliveryResponse) {

        userIdx = response.result.userIdx

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

        pointUse = temporaryShared.getInt("pointUse", 0)
        if (pointUse != 0) {
            temporary = true
            binding.buyDeliveryPoint.setText(pointUse.toString())
            pointCalculate()
        }

        dismissLoadingDialog()
    }

    override fun onGetBuyDeliveryFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetUserShippingSuccess(response: BuyDeliveryAddressManageResponse) {

        Log.d("responsesss", response.result.toString())
        if (!response.result.isNullOrEmpty()) {
            Log.d("responsesss", "진입")
            areaRegisterRv.visibility = View.VISIBLE
            areaRegisterEmpty.visibility = View.GONE

            binding.buyDeliveryAreaRegister.visibility = View.GONE
            binding.buyDeliveryAreaRegisterAddress.visibility = View.VISIBLE
            binding.buyDeliveryAreaRegisterName.visibility = View.VISIBLE
            binding.buyDeliveryAreaRegisterPhone.visibility = View.VISIBLE
            registerAddress = response.result[0].address
            registerDetailAddress = response.result[0].detailAddress
            registerName = response.result[0].receiverName
            registerPhone = response.result[0].receiverPhoneNum
            areaRegister()

            areaSelectItem = response.result

            areaRegisterRv.adapter = BuyDeliveryAreaSelectAdapter(areaSelectItem)


        } else {
            Log.d("responsesss", "dlrjs")
            areaRegisterRv.visibility = View.GONE
            areaRegisterEmpty.visibility = View.VISIBLE
            binding.buyDeliveryAreaRegister.visibility = View.VISIBLE
            binding.buyDeliveryAreaRegisterAddress.visibility = View.GONE
            binding.buyDeliveryAreaRegisterName.visibility = View.GONE
            binding.buyDeliveryAreaRegisterPhone.visibility = View.GONE
        }
        dismissLoadingDialog()
    }

    override fun onGetUserShippingFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostUserShippingSuccess(response: BuyDeliveryAddressAddResponse) {}

    override fun onPostUserShippingFailure(message: String) {}

    override fun onPostBuyDeliveryPaymentSuccess(response: BuyDeliveryPaymentResponse) {
        // temporary 비우기
        //temporaryeditor.clear()
        //temporaryeditor.apply()

        if (response.code == 1000) {
            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
            overridePendingTransition(0, 0)
            showCustomToast("결제성공")
        }
        dismissLoadingDialog()
    }

    override fun onPostBuyDeliveryPaymentFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onDestroy() {
        // temporary 비우기
        temporaryeditor.clear()
        temporaryeditor.commit()
        super.onDestroy()
        animation.stop()
    }
}