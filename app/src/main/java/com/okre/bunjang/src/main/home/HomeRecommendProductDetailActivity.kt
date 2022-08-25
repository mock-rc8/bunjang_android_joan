package com.okre.bunjang.src.main.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityHomeRecommendProductDetailBinding

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

        val shipBtn = safePayBottomSheetView.findViewById<ConstraintLayout>(R.id.dialog_safe_pay_ship_layout)
        val shipCheckbox = safePayBottomSheetView.findViewById<CheckBox>(R.id.dialog_safe_pay_ship_checkbox)
        val directBtn = safePayBottomSheetView.findViewById<ConstraintLayout>(R.id.dialog_safe_pay_direct_layout)
        val directCheckbox = safePayBottomSheetView.findViewById<CheckBox>(R.id.dialog_safe_pay_direct_checkbox)

        shipBtn.setOnClickListener {
            shipCheckbox.isChecked = true
            shipBtn.setBackgroundResource(R.drawable.background_btn_safe_pay_check)
            directCheckbox.isChecked = false
            directBtn.setBackgroundResource(R.drawable.background_btn_safe_pay_check)
            safePayBottomSheetDialog.dismiss()
            //val val intent = Intent(this, )
            //startActivity(intent)
        }

        directBtn.setOnClickListener {
            directCheckbox.isChecked = true
            directBtn.setBackgroundResource(R.drawable.background_btn_safe_pay_check)
            shipCheckbox.isChecked = false
            shipBtn.setBackgroundResource(R.drawable.background_btn_safe_pay_check)
            safePayBottomSheetDialog.dismiss()
            //val val intent = Intent(this, )
            //startActivity(intent)
        }
    }
}