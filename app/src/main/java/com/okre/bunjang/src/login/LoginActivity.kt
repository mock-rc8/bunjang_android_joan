package com.okre.bunjang.src.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.okre.bunjang.R
import com.okre.bunjang.config.BaseActivity
import com.okre.bunjang.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    var viewPagerList = mutableListOf<Int>()
    private lateinit var handler : Handler
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setViewPager()

        handler = Handler(Looper.getMainLooper()) {
            setViewPagerPage()
            true
        }

        val thread = Thread(PagerRunnable())
        thread.start()

    }

    fun setViewPager() {
        viewPagerList.add(R.drawable.image_viewpager01)
        viewPagerList.add(R.drawable.image_viewpager02)
        viewPagerList.add(R.drawable.image_viewpager03)
        viewPagerList.add(R.drawable.image_viewpager04)
        binding.loginViewpager.adapter = ViewPagerAdapter(viewPagerList)
    }

    fun setViewPagerPage() {
        // 페이지 변화
        if(currentPosition == 4) {
            currentPosition = 0
        }
        binding.loginViewpager.setCurrentItem(currentPosition, false)
        currentPosition++

        // 인디케이터
        binding.loginViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.indicator1.setImageDrawable(getDrawable(R.drawable.shape_viewpager_indicator))
                binding.indicator2.setImageDrawable(getDrawable(R.drawable.shape_viewpager_indicator))
                binding.indicator3.setImageDrawable(getDrawable(R.drawable.shape_viewpager_indicator))
                binding.indicator4.setImageDrawable(getDrawable(R.drawable.shape_viewpager_indicator))

                when(position) {
                    0 -> binding.indicator1.setImageDrawable(getDrawable(R.drawable.shape_viewpager_indicator_select))
                    1 -> binding.indicator2.setImageDrawable(getDrawable(R.drawable.shape_viewpager_indicator_select))
                    2 -> binding.indicator3.setImageDrawable(getDrawable(R.drawable.shape_viewpager_indicator_select))
                    3 -> binding.indicator4.setImageDrawable(getDrawable(R.drawable.shape_viewpager_indicator_select))
                }
            }
        })
    }

    inner class PagerRunnable : Runnable{
        override fun run() {
            while(true){
                try {
                    Thread.sleep(5000)
                    handler.sendEmptyMessage(0)
                } catch (e : InterruptedException){
                    Log.d("error", e.message.toString())
                }
            }
        }
    }
}