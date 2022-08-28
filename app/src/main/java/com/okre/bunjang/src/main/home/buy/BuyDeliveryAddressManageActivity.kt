package com.okre.bunjang.src.main.home.buy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityBuyDeliveryAddressManageBinding
import com.okre.bunjang.src.main.home.buy.adapter.BuyDeliveryAddressManageAdapter
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryAddressManageItem
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryAreaSelectItem

class BuyDeliveryAddressManageActivity : BaseActivity<ActivityBuyDeliveryAddressManageBinding>(ActivityBuyDeliveryAddressManageBinding::inflate) {

    var addressManageItem = mutableListOf<BuyDeliveryAddressManageItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 주소 없으면
        binding.addressManageEmptyLayout.visibility = View.VISIBLE
        binding.addressManageRv.visibility = View.GONE

        // 주소 있으면
        binding.addressManageEmptyLayout.visibility = View.GONE
        binding.addressManageRv.visibility = View.VISIBLE

        binding.addressManageRv.adapter = BuyDeliveryAddressManageAdapter(addressManageItem)

        // 주소 추가 버튼 클릭
        binding.addressManageAddTxt.setOnClickListener {
            binding.addressManageTitle.text = "주소 추가"
            binding.addressManageAdd.visibility = View.VISIBLE
            binding.addressManageAddButton.visibility = View.VISIBLE
        }

        //글자 입력 버튼 enable

        // 주소 추가 완료 버튼 클릭
        binding.addressManageAddButton.setOnClickListener {
            binding.addressManageAdd.visibility = View.GONE
            binding.addressManageAddButton.visibility = View.GONE
            binding.addressManageTitle.text = "주소 관리"
        }

    }
}