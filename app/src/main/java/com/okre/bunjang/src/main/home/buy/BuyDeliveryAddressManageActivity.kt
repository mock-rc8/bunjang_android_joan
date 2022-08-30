package com.okre.bunjang.src.main.home.buy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityBuyDeliveryAddressManageBinding
import com.okre.bunjang.src.login.model.LoginRequest
import com.okre.bunjang.src.main.home.buy.adapter.BuyDeliveryAddressManageAdapter
import com.okre.bunjang.src.main.home.buy.adapter.BuyDeliveryAreaSelectAdapter
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryAddressManageItem
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryAreaSelectItem
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryAddressAddRequest
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryAddressAddResponse
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryAddressManageResponse
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryResponse

class BuyDeliveryAddressManageActivity : BaseActivity<ActivityBuyDeliveryAddressManageBinding>(ActivityBuyDeliveryAddressManageBinding::inflate),
    BuyDeliveryInterface {

    private var buyDeliveryAddressManageAdapter = BuyDeliveryAddressManageAdapter()

    private var nameEdit = false
    private var phoneEdit = false
    private var addressEdit = false
    private var detailAddressEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 배송지 appi
        showLoadingDialog(this)
        BuyDeliveryService(this).tryGetUserShipping()


        binding.addressManageRv.adapter = buyDeliveryAddressManageAdapter

        // 주소 추가 버튼 클릭
        binding.addressManageAddTxt.setOnClickListener {
            binding.addressManageEmptyLayout.visibility = View.GONE
            binding.addressManageRv.visibility = View.GONE
            binding.addressManageTitle.text = "주소 추가"
            binding.addressManageAdd.visibility = View.VISIBLE
            binding.addressManageAddButton.visibility = View.VISIBLE
        }

        //글자 입력 버튼 enable
        addressAddEdittext()

        // 주소 추가 완료 버튼 클릭
        postAddress()

    }

    fun postAddress() {
        binding.addressManageAddButton.setOnClickListener {
            val address: String = binding.addressManageAddAddress.text.toString()
            val detailAddress: String = binding.addressManageAddAddressDetail.text.toString()
            val receiverName: String = binding.addressManageAddName.text.toString()
            val receiverPhoneNum: String = binding.addressManageAddPhone.text.toString()

            val buyDeliveryAddressAddRequest = BuyDeliveryAddressAddRequest(address, detailAddress, receiverName, receiverPhoneNum)
            showLoadingDialog(this)
            BuyDeliveryService(this).tryPosstUserShipping(buyDeliveryAddressAddRequest)
        }

    }

    fun addressAddEdittext() {
        binding.addressManageAddName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                nameEdit = p0 != null && !p0.toString().equals("")
                buttonEnable()
            }
        })
        binding.addressManageAddPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                phoneEdit = p0 != null && !p0.toString().equals("")
                buttonEnable()
            }
        })
        binding.addressManageAddAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                addressEdit = p0 != null && !p0.toString().equals("")
                buttonEnable()
            }
        })
        binding.addressManageAddAddressDetail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                detailAddressEdit = p0 != null && !p0.toString().equals("")
                buttonEnable()
            }
        })

    }

    fun buttonEnable() {
        binding.addressManageAddButton.isEnabled = nameEdit && phoneEdit && addressEdit && detailAddressEdit
    }

    override fun onGetBuyDeliverySuccess(response: BuyDeliveryResponse) {}

    override fun onGetBuyDeliveryFailure(message: String) {}

    override fun onGetUserShippingSuccess(response: BuyDeliveryAddressManageResponse) {
        if (!response.result.isNullOrEmpty()) {
            // 주소 있으면
            binding.addressManageEmptyLayout.visibility = View.GONE
            binding.addressManageRv.visibility = View.VISIBLE
            for (shippingList in response.result) {
                val address = shippingList.address
                val detailAddress = shippingList.detailAddress
                val name = shippingList.receiverName
                val phone = shippingList.receiverPhoneNum
                buyDeliveryAddressManageAdapter.addList(BuyDeliveryAddressManageItem(address, detailAddress, name, phone))
            }
        } else {
            // 주소 없으면
            binding.addressManageEmptyLayout.visibility = View.VISIBLE
            binding.addressManageRv.visibility = View.GONE
        }
        dismissLoadingDialog()
    }

    override fun onGetUserShippingFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostUserShippingSuccess(response: BuyDeliveryAddressAddResponse) {
        if (response.code == 1000) {
            binding.addressManageAdd.visibility = View.GONE
            binding.addressManageAddButton.visibility = View.GONE
            binding.addressManageTitle.text = "주소 관리"
            binding.addressManageRv.visibility = View.VISIBLE

//            BuyDeliveryService(this).tryGetUserShipping()

//            val intent = Intent(this, BuyDeliveryAddressManageActivity::class.java)
//            finish()
//            startActivity(intent)
        }
        dismissLoadingDialog()
    }

    override fun onPostUserShippingFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}