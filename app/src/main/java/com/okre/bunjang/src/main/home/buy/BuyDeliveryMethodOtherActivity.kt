package com.okre.bunjang.src.main.home.buy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityBuyDeliveryMethodOtherBinding
import com.okre.bunjang.src.main.home.buy.adapter.BuyDeliveryMethodOtherAdapter
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryMethodOtherItem

class BuyDeliveryMethodOtherActivity : BaseActivity<ActivityBuyDeliveryMethodOtherBinding>(ActivityBuyDeliveryMethodOtherBinding::inflate) {

    var productIdx = 0

    var item : MutableList<BuyDeliveryMethodOtherItem> = arrayListOf()
    private var rvAdapter = BuyDeliveryMethodOtherAdapter(item)

    private var paymentMethodOtherSelect : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productIdx = intent.getIntExtra("productIdx", 0)

        methodOtherRecyclerview()

        methodOtherBtnClick()
    }

    fun methodOtherRecyclerview() {
        item.add(BuyDeliveryMethodOtherItem("신용/체크카드", true))
        item.add(BuyDeliveryMethodOtherItem("카카오페이", false))
        item.add(BuyDeliveryMethodOtherItem("네이버페이", false))
        item.add(BuyDeliveryMethodOtherItem("토스", false))
        item.add(BuyDeliveryMethodOtherItem("간편계좌결제", false))
        item.add(BuyDeliveryMethodOtherItem("휴대폰결제", false))
        item.add(BuyDeliveryMethodOtherItem("편의점결제", false))
        item.add(BuyDeliveryMethodOtherItem("무통장(가상계좌)", false))
        item.add(BuyDeliveryMethodOtherItem("차이", false))
        binding.methodOtherRv.adapter = rvAdapter
        paymentMethodOtherSelect = "신용/체크카드"

        rvAdapter.itemClick = object : BuyDeliveryMethodOtherAdapter.ItemClick {
            override fun onClick(view: View, position: Int, text: String) {
                paymentMethodOtherSelect = text
                when (text) {
                    "신용/체크카드" -> {
                        binding.methodOtherSecondSelectLayout.visibility = View.VISIBLE
                        binding.methodOtherNoticeTitle.text = getString(R.string.method_other_notice_title_card)
                        binding.methodOtherNoticeContent.text = getString(R.string.method_other_notice_content_card)
                        binding.methodOtherNoticeInfo.text = getString(R.string.method_other_notice_content_card_2)
                    }
                    "카카오페이" -> {
                        binding.methodOtherSecondSelectLayout.visibility = View.GONE
                        binding.methodOtherNoticeTitle.text = getString(R.string.method_other_notice_title_kakaopay)
                        binding.methodOtherNoticeContent.text = getString(R.string.method_other_notice_content_kakaopay)
                        binding.methodOtherNoticeInfo.visibility = View.GONE
                    }
                    "네이버페이" -> {
                        binding.methodOtherSecondSelectLayout.visibility = View.GONE
                        binding.methodOtherNoticeTitle.text = getString(R.string.method_other_notice_title_naverpay)
                        binding.methodOtherNoticeContent.text = getString(R.string.method_other_notice_content_naverpay)
                        binding.methodOtherNoticeInfo.visibility = View.GONE
                    }
                    "토스" -> {
                        binding.methodOtherSecondSelectLayout.visibility = View.GONE
                        binding.methodOtherNoticeTitle.text = getString(R.string.method_other_notice_title_toss)
                        binding.methodOtherNoticeContent.text = getString(R.string.method_other_notice_content_toss)
                        binding.methodOtherNoticeInfo.visibility = View.GONE
                        }
                    "간편계좌결제" -> {
                        binding.methodOtherSecondSelectLayout.visibility = View.GONE
                        binding.methodOtherNoticeTitle.text = getString(R.string.method_other_notice_title_simple)
                        binding.methodOtherNoticeContent.text = getString(R.string.method_other_notice_content_simple)
                        binding.methodOtherNoticeInfo.visibility = View.GONE
                    }
                    "휴대폰결제" -> {
                        binding.methodOtherSecondSelectLayout.visibility = View.GONE
                        binding.methodOtherNoticeTitle.text = getString(R.string.method_other_notice_title_phone)
                        binding.methodOtherNoticeContent.text = getString(R.string.method_other_notice_content_phone)
                        binding.methodOtherNoticeInfo.visibility = View.GONE
                    }
                    "편의점결제" -> {
                        binding.methodOtherSecondSelectLayout.visibility = View.GONE
                        binding.methodOtherNoticeTitle.text = getString(R.string.method_other_notice_title_convenience)
                        binding.methodOtherNoticeContent.text = getString(R.string.method_other_notice_content_convenience)
                        binding.methodOtherNoticeInfo.text = getString(R.string.method_other_notice_content_convenience_2)
                    }
                    "무통장(가상계좌)" -> {
                        binding.methodOtherSecondSelectLayout.visibility = View.VISIBLE
                        binding.methodOtherNoticeTitle.text = getString(R.string.method_other_notice_title_bank)
                        binding.methodOtherNoticeContent.text = getString(R.string.method_other_notice_content_bank)
                        binding.methodOtherNoticeInfo.visibility = View.GONE
                    }
                    "차이" -> {
                        binding.methodOtherSecondSelectLayout.visibility = View.GONE
                        binding.methodOtherNoticeTitle.text = getString(R.string.method_other_notice_title_chai)
                        binding.methodOtherNoticeContent.text = getString(R.string.method_other_notice_content_chai)
                        binding.methodOtherNoticeInfo.visibility = View.GONE
                    }
                }
            }
        }
    }

    fun methodOtherBtnClick() {
        binding.methodOtherBtnNext.setOnClickListener {
            val intent = Intent(this, BuyDeliveryActivity::class.java)
            intent.putExtra("paymentMethodOtherSelect", paymentMethodOtherSelect)
            intent.putExtra("productIdx", productIdx)
            startActivity(intent)
            finish()
        }
    }
}