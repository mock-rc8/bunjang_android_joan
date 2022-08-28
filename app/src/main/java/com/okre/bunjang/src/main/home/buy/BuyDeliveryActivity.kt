package com.okre.bunjang.src.main.home.buy

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityBuyDeliveryBinding
import com.okre.bunjang.src.main.home.buy.adapter.BuyDeliveryAreaSelectAdapter
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryAreaSelectItem

class BuyDeliveryActivity : BaseActivity<ActivityBuyDeliveryBinding>(ActivityBuyDeliveryBinding::inflate) {

    lateinit var animation : AnimationDrawable
    var areaSelectItem = mutableListOf<BuyDeliveryAreaSelectItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 택배거래 or 직거래
        if (intent.getStringExtra("selectDeliveryOrDirect") == "Direct") {
            binding.buyDeliveryOrDirectTitle.text = getString(R.string.buy_direct_title)
            binding.buyDeliveryAreaLayout.visibility = View.GONE
        }

        // 코인 이미지 회전
        animation = binding.buyDeliveryPaymentSpinningCoin.background as AnimationDrawable
        animation.start()

        // 배송지 등록
        clickAddressRegister()
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

        // 배송지 있으면 rv visibility visible 없으면 gone
        val areaRegisterRv = bottomSheetView.findViewById<RecyclerView>(R.id.dialog_area_rv)
        areaRegisterRv.adapter = BuyDeliveryAreaSelectAdapter(areaSelectItem)

    }

    override fun onDestroy() {
        super.onDestroy()
        animation.stop()
    }
}