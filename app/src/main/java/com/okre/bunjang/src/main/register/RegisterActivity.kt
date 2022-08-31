package com.okre.bunjang.src.main.register

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import com.google.firebase.storage.FirebaseStorage
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityRegisterBinding
import com.okre.bunjang.src.main.home.buy.adapter.BuyDeliveryAreaSelectAdapter
import com.okre.bunjang.src.main.home.buy.model.BuyDeliveryAddressManageResult
import com.okre.bunjang.src.main.register.adapter.RegisterAdapter
import com.okre.bunjang.src.main.register.adapter.RegisterTagAdapter
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(ActivityRegisterBinding::inflate) {
    val PERMISSION_STORAGE_REQUEST_CODE = 101
    private lateinit var permissionSharedPreferences: SharedPreferences
    private lateinit var permissionEditor: SharedPreferences.Editor
    var selectImage : Uri? = null
    var imageItem = mutableListOf<String>()

    var mainImageBoolean : Boolean = true
    var image : String? = null
    var subImage : MutableList<String> = arrayListOf()

    lateinit var registerShared : SharedPreferences
    lateinit var registerEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionSharedPreferences = getSharedPreferences("permission", AppCompatActivity.MODE_PRIVATE)
        permissionEditor = permissionSharedPreferences.edit()

        registerShared = getSharedPreferences("register", MODE_PRIVATE)
        registerEditor = registerShared.edit()

        imageBtnClick()

        categoryBtnClick()

        tagBtnClick()
    }

    override fun onResume() {
        super.onResume()
        val tagShared = registerShared.getString("tag", null).toString()
        if (tagShared == "null" || tagShared == "") {

        } else {
            var tagItem : MutableList<String> = tagShared.split(" ").toMutableList()
            var tagAdapter = RegisterTagAdapter(tagItem)
            binding.registerImgTag.visibility = View.GONE
            binding.registerTxtTag.visibility = View.GONE
            binding.registerRvTag.visibility = View.VISIBLE
            binding.registerRvTag.adapter = tagAdapter
        }
    }

    fun tagBtnClick() {
        binding.registerBtnTag.setOnClickListener {

        }
    }

    fun categoryBtnClick() {
        binding.registerBtnCategory.setOnClickListener {
            val intent = Intent(this, RegisterTagActivity::class.java)
            startActivity(intent)
        }

    }

    fun imageBtnClick() {
        binding.registerBtnGallery.setOnClickListener {
            if (checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) { // 권한 확인 -> 긍정 -> 갤러리 실행
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
            showLoadingDialog(this)
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
        val builder = AlertDialog.Builder(this)
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


}