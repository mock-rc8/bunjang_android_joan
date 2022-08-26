package com.okre.bunjang.src.main.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityHomeRecommendProductDetailBinding
import com.okre.bunjang.src.main.home.buy.BuyDeliveryActivity

class HomeRecommendProductDetailActivity : BaseActivity<ActivityHomeRecommendProductDetailBinding>(ActivityHomeRecommendProductDetailBinding::inflate) {

    private lateinit var safePayBottomSheetDialog : BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        safePayDialog()

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
            directBtn.setBackgroundResource(R.drawable.background_btn_safe_pay_check)
        }

        directBtn.setOnClickListener {
            directCheckbox.isChecked = true
            directBtn.setBackgroundResource(R.drawable.background_btn_safe_pay_check)
            deliveryCheckbox.isChecked = false
            deliveryBtn.setBackgroundResource(R.drawable.background_btn_safe_pay_check)
        }

        nextBtn.setOnClickListener {
            safePayBottomSheetDialog.dismiss()
            if (deliveryCheckbox.isChecked) {
                val intent = Intent(this, BuyDeliveryActivity::class.java)
                startActivity(intent)
            } else {
                // 직거래 activity
            }
        }
    }

}