package com.okre.bunjang.src.main.home.buy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityBuyDeliveryAddressManageBinding
import com.okre.bunjang.src.main.home.buy.adapter.BuyDeliveryAddressManageAdapter
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryAddressManageItem
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryAreaSelectItem

class BuyDeliveryAddressManageActivity : BaseActivity<ActivityBuyDeliveryAddressManageBinding>(ActivityBuyDeliveryAddressManageBinding::inflate) {

    var addressManageItem = mutableListOf<BuyDeliveryAddressManageItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.addressManagerRv.adapter = BuyDeliveryAddressManageAdapter(addressManageItem)

    }
}