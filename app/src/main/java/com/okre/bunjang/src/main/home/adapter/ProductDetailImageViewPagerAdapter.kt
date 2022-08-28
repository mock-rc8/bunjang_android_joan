package com.okre.bunjang.src.main.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.okre.bunjang.databinding.ItemProductDetailImeageViewPagerBinding

class ProductDetailImageViewPagerAdapter : RecyclerView.Adapter<ProductDetailImageViewPagerAdapter.PagerViewHolder>() {

    //val list : MutableList<String>
    private val list : MutableList<String> = arrayListOf()

    inner class PagerViewHolder(val binding: ItemProductDetailImeageViewPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(list : String) {
            Glide.with(binding.itemProductDetailImage.context)
                .load(list)
                .into(binding.itemProductDetailImage)
        }
    }

    fun addList(image: String) {
        list.add(image)
        notifyItemInserted(list.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = ItemProductDetailImeageViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bindList(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}