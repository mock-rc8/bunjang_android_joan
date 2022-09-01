package com.okre.bunjang.src.main.register

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.storage.FirebaseStorage
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseFragment
import com.okre.bunjang.databinding.FragmentRegisterBinding
import com.okre.bunjang.src.login.model.SignRequest
import com.okre.bunjang.src.main.home.HomeFragment
import com.okre.bunjang.src.main.register.adapter.RegisterAdapter
import com.okre.bunjang.src.main.register.adapter.RegisterTagAdapter
import com.okre.bunjang.src.main.register.model.RegisterRequest
import com.okre.bunjang.src.main.register.model.RegisterResponse
import com.okre.bunjang.src.main.register.model.UserIdxResponse
import com.okre.bunjang.util.KeyboardVisibilityUtils
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::bind, R.layout.fragment_register),
    RegisterInterface {

    val PERMISSION_STORAGE_REQUEST_CODE = 101
    private lateinit var permissionSharedPreferences: SharedPreferences
    private lateinit var permissionEditor: SharedPreferences.Editor
    var selectImage : Uri? = null
    var imageItem = mutableListOf<String>()

    var mainImageBoolean : Boolean = true


    lateinit var registerShared : SharedPreferences
    lateinit var registerEditor: SharedPreferences.Editor

    private lateinit var optionBottomSheetDialog : BottomSheetDialog

    private lateinit var callback : OnBackPressedCallback

    var formatPrice : String = ""

    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    // registerRequest
    var image : String = ""
    var subImage : MutableList<String> = arrayListOf()
    var productsName: String = ""
    var mainCategory: String = ""
    var subCategory: String = ""
    var hashtagText: String = ""
    var price: Int = 0
    var shippingFee: String = "배송비별도"
    var quantity: Int = 0
    var productStatus: String = "중고상품"
    var exchange: String = "교환불가"
    var address: String = "지역정보 없음"
    var pay: Int = 1 // 0 사용x, 1 사용
    var productExplaination: String = ""
    var userIdx: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireContext())
        RegisterService(this).tryGetUserIdx()

        hideBottomNavigation(true)

        permissionSharedPreferences = requireActivity().getSharedPreferences("permission", AppCompatActivity.MODE_PRIVATE)
        permissionEditor = permissionSharedPreferences.edit()

        registerShared = requireActivity().getSharedPreferences("register", AppCompatActivity.MODE_PRIVATE)
        registerEditor = registerShared.edit()

        imageBtnClick()

        categoryBtnClick()

        tagBtnClick()

        optionClick()

        registerBtnClick()

        backBtnClick()

        priceEdit()

        contentWrite()

        safePayBtnClick()

        keyboard()

        keyboardVisibilityUtils = KeyboardVisibilityUtils(requireActivity().window,
            onShowKeyboard = { keyboardHeight ->
                binding.registerScrollView.run {
                    smoothScrollTo(scrollX, scrollY + keyboardHeight)
                }
            })

        binding.fragmentRoot.setOnClickListener {
            hideKeyboard()
        }

    }
    fun hideKeyboard(){
        if(activity != null && requireActivity().currentFocus != null){
            val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            binding.registerLayoutSafePay.visibility = View.VISIBLE
        }
    }

    fun keyboard() {
        binding.registerEdtProductName.setOnFocusChangeListener { p0, p1 ->
            if (p1) {
                binding.registerLayoutSafePay.visibility = View.GONE
                binding.registerAdditionalTitle.visibility = View.VISIBLE
            } else {
                binding.registerAdditionalTitle.visibility = View.GONE
            }
        }

        binding.registerEdtProductPrice.setOnFocusChangeListener { p0, p1 ->
            if (p1) {
                binding.registerLayoutSafePay.visibility = View.GONE
            } else {

            }
        }
        binding.registerEdtProductContent.setOnFocusChangeListener { p0, p1 ->
            if (p1) {
                binding.registerLayoutSafePay.visibility = View.GONE
                binding.registerAdditionalContent.visibility = View.VISIBLE
                binding.scrollHeight.visibility = View.VISIBLE
            } else {
                binding.registerAdditionalContent.visibility = View.GONE
                binding.scrollHeight.visibility = View.GONE
            }
        }


    }

    fun safePayBtnClick() {
        binding.registerBtnSafepay.setOnClickListener {
            if (pay == 1) { //번개 페이 사용 -> 사용 안함
                pay = 0
                binding.registerBtnSafepay.setBackgroundResource(R.drawable.background_register_gray_radius)
                binding.registerTxtSafepay.setTextColor(ContextCompat.getColor(requireContext(), R.color.register_gray))
                binding.registerImgSafepay.setBackgroundResource(R.drawable.icon_terms_unchecked)
            } else {
                pay = 1
                binding.registerBtnSafepay.setBackgroundResource(R.drawable.background_btn_safe_pay_check)
                binding.registerTxtSafepay.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                binding.registerImgSafepay.setBackgroundResource(R.drawable.icon_terms_checked)
            }
        }
    }

    fun contentWrite() {
        binding.registerEdtProductContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                binding.registerCountContent.text = getString(R.string.register_count, p0?.length)
            }

        })
    }

    fun priceEdit() {
        binding.registerEdtProductPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //p0.toString() != formatPrice 해줘야 무한반복하지 않음
                if(!p0.isNullOrEmpty() && p0.toString() != formatPrice) {
                    price = p0.toString().replace(",", "").toInt()
                    formatPrice = DecimalFormat("#,###").format(price)
                    binding.registerEdtProductPrice.setText(formatPrice)
                    binding.registerEdtProductPrice.setSelection(formatPrice.length) //커서 위치
                    binding.registerImgWon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                } else if (p0.isNullOrEmpty()){
                    binding.registerImgWon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.register_gray))
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.registerBtnIncludeFee.setOnClickListener {
            if (shippingFee == "배송비별도") {
                binding.registerCheckIncludeFee.setImageResource(R.drawable.icon_register_shipping_check)
                shippingFee = "배송비포함"
            } else {
                binding.registerCheckIncludeFee.setImageResource(R.drawable.icon_register_shipping_uncheck)
                shippingFee = "배송비별도"
            }
        }

    }

    fun backBtnClick() {
        binding.registerBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout, HomeFragment()).commit()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("ddddd", "onreeee")
        val tagShared = registerShared.getString("tag", null).toString()
        if (tagShared == "null" || tagShared == "") {

        } else {
            hashtagText = tagShared.replace(" ", ",")
            var tagItem : MutableList<String> = tagShared.split(" ").toMutableList()
            var tagAdapter = RegisterTagAdapter(tagItem)
            binding.registerImgTag.visibility = View.GONE
            binding.registerTxtTag.visibility = View.GONE
            binding.registerRvTag.visibility = View.VISIBLE
            binding.registerRvTag.adapter = tagAdapter
        }
        mainCategory = registerShared.getString("mainCategory", null).toString()
        subCategory = registerShared.getString("subCategory", null).toString()
        if (subCategory == "null" || subCategory == "") {

        } else {
            binding.registerTxtCategoryLarge.text = mainCategory
            binding.registerTxtCategoryLarge.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.registerImgCategoryDivider.visibility = View.VISIBLE
            binding.registerTxtCategorySmall.visibility = View.VISIBLE
            binding.registerTxtCategorySmall.text = subCategory
        }
    }

    fun registerBtnClick() {
        binding.registerBtnRegister.setOnClickListener {
            ///////post api
            productsName = binding.registerEdtProductName.text.toString()

            productExplaination = binding.registerEdtProductContent.text.toString()

            val registerRequest = RegisterRequest(address, exchange, hashtagText, image, mainCategory, pay, price, productExplaination, productStatus, productsName, quantity, shippingFee, subCategory, subImage, userIdx)

            // 지우기
            registerEditor.clear()
            registerEditor.apply()

            showLoadingDialog(requireContext())
            RegisterService(this).tryPostNew(registerRequest)
        }
    }

    fun optionClick() {
        binding.registerBtnChooseOptions.setOnClickListener {
            val optionBottomSheetView = layoutInflater.inflate(R.layout.dialog_register_options, null)
            optionBottomSheetDialog = BottomSheetDialog(requireActivity())
            optionBottomSheetDialog.setContentView(optionBottomSheetView)
            optionBottomSheetDialog.show()

            val optionCount = optionBottomSheetView.findViewById<EditText>(R.id.register_edt_product_count)
            val optionOld = optionBottomSheetView.findViewById<Button>(R.id.register_btn_old)
            val optionNew = optionBottomSheetView.findViewById<Button>(R.id.register_btn_new)
            val optionNoExchange = optionBottomSheetView.findViewById<Button>(R.id.register_btn_no_exchange)
            val optionExchange = optionBottomSheetView.findViewById<Button>(R.id.register_btn_exchange)
            val optionFinish = optionBottomSheetView.findViewById<Button>(R.id.register_btn_finish_options)

            optionCount.setText("1")
            binding.registerTxtProductCount.text = getString(R.string.register_product_count, 1)

            optionOld.isSelected = true
            optionNew.isSelected = false

            optionNoExchange.isSelected = true
            optionExchange.isSelected = false

            optionOld.setOnClickListener {
                productStatus = "중고상품"
                optionOld.isSelected = true
                optionOld.setTextColor(ContextCompat.getColor(requireContext(), R.color.bunjang_red))
                optionNew.isSelected = false
                optionNew.setTextColor(ContextCompat.getColor(requireContext(), R.color.register_gray))
            }

            optionNew.setOnClickListener {
                productStatus = "새상품"
                optionOld.isSelected = false
                optionOld.setTextColor(ContextCompat.getColor(requireContext(), R.color.register_gray))
                optionNew.isSelected = true
                optionNew.setTextColor(ContextCompat.getColor(requireContext(), R.color.bunjang_red))
            }

            optionNoExchange.setOnClickListener {
                exchange = "교환불가"
                optionNoExchange.isSelected = true
                optionNoExchange.setTextColor(ContextCompat.getColor(requireContext(), R.color.bunjang_red))
                optionExchange.isSelected = false
                optionExchange.setTextColor(ContextCompat.getColor(requireContext(), R.color.register_gray))
            }

            optionExchange.setOnClickListener {
                exchange = "교환가능"
                optionNoExchange.isSelected = false
                optionNoExchange.setTextColor(ContextCompat.getColor(requireContext(), R.color.register_gray))
                optionExchange.isSelected = true
                optionExchange.setTextColor(ContextCompat.getColor(requireContext(), R.color.bunjang_red))
            }

            optionFinish.setOnClickListener {
                quantity = optionCount.text.toString().toInt()
                binding.registerTxtProductCount.text = getString(R.string.register_product_count, quantity)
                binding.registerTxtProductIsUsed.text = productStatus
                binding.registerTxtCanChange.text = exchange
                optionBottomSheetDialog.dismiss()
            }
        }
    }

    fun tagBtnClick() {
        binding.registerBtnTag.setOnClickListener {
            val intent = Intent(activity, RegisterTagActivity::class.java)
            startActivity(intent)
        }
    }

    fun categoryBtnClick() {
        binding.registerBtnCategory.setOnClickListener {
            val intent = Intent(activity, RegisterCategoryActivity::class.java)
            startActivity(intent)
        }

    }

    fun imageBtnClick() {
        binding.registerBtnGallery.setOnClickListener {
            if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) { // 권한 확인 -> 긍정 -> 갤러리 실행
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                loadImage.launch(intent)
            } else { // 권한 확인 -> 처음, 거부
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) { // deny만 했을 경우
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_STORAGE_REQUEST_CODE)
                } else { // first, Deny&don't ask again 눌렀을 경우
                    val permissionFirst = permissionSharedPreferences.getString("first", "true")
                    if (permissionFirst != "false") { // first
                        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_STORAGE_REQUEST_CODE)
                    } else {// Deny&don't ask again
                        showDialogToGetPermission()
                    }
                }
            }
        }
    }

    val loadImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == AppCompatActivity.RESULT_OK) {
            showLoadingDialog(requireContext())
            selectImage = it.data?.data

            var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            var imgFileName = "IMAGE_" + timeStamp + "_.png"

            val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://register-image-d6d1c.appspot.com")
            val storageReference = storage.reference
            val pathReference = storageReference.child("image").child(imgFileName)
            pathReference.putFile(selectImage!!).addOnSuccessListener {
                val result = it.metadata!!.reference!!.downloadUrl
                result.addOnSuccessListener {
                    var imageLink = it.toString()
                    if (mainImageBoolean) {
                        image = imageLink
                        mainImageBoolean = false
                    } else {
                        subImage.add(imageLink)
                    }
                    imageItem.add(imageLink)
                    binding.registerRvGallery.adapter = RegisterAdapter(imageItem)
                    dismissLoadingDialog()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSION_STORAGE_REQUEST_CODE -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 허용
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    loadImage.launch(intent)
                } else {
                    permissionEditor.putString("first", "false")
                    permissionEditor.apply()
                    showCustomToast("거절하였습니다.")
                }
            }
        }
    }

    fun showDialogToGetPermission() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("저장 공간, 카메라 권한을 허용해야 사진 등록이 가능합니다. 권한을 설정하시겠습니까?")

        builder.setPositiveButton("설정하기", DialogInterface.OnClickListener {
                dialogInterface, i ->
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_STORAGE_REQUEST_CODE)
//                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", requireActivity().packageName, null))
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
        })

        builder.setNegativeButton("취소") { dialogInterface, i ->
            // ignore
        }

        builder.create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideBottomNavigation(false)
        keyboardVisibilityUtils.detachKeyboardListeners()
    }

    fun hideBottomNavigation(boolean: Boolean) {
        val bottomNavigation = requireActivity().findViewById<BottomNavigationView>(R.id.main_btm_nav)
        if (boolean) {
            bottomNavigation.visibility = View.GONE
        } else {
            bottomNavigation.visibility = View.VISIBLE
        }
    }

    override fun onGetUserIdxSuccess(response: UserIdxResponse) {
        userIdx = response.result.userIdx
        dismissLoadingDialog()
    }

    override fun onGetUserIdxFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostNewSuccess(response: RegisterResponse) {
        dismissLoadingDialog()
        showCustomToast(response.message)
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout, HomeFragment()).commit()
    }

    override fun onPostNewFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}