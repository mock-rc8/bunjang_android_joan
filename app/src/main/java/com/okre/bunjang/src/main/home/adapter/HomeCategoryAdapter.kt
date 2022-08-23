package com.okre.bunjang.src.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okre.bunjang.databinding.ItemHomeCategoryBinding
import com.okre.bunjang.src.main.home.item.HomeCategoryItem

class HomeCategoryAdapter(val itemList : MutableList<HomeCategoryItem>) : RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemHomeCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(item : HomeCategoryItem) {
            item.cartegoryImage?.let { binding.homeRvCategoryItemImageview.setImageResource(it) }
            binding.homeRvCategoryItemTextview.text = item.categoryName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHomeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindList(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}