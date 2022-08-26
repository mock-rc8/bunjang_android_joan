package com.okre.bunjang.src.main.home.buy

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityBuyDeliveryBinding

class BuyDeliveryActivity : BaseActivity<ActivityBuyDeliveryBinding>(ActivityBuyDeliveryBinding::inflate) {

    lateinit var animation : AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animation = binding.buyDeliveryPaymentSpinningCoin.background as AnimationDrawable
        animation.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        animation.stop()
    }
}