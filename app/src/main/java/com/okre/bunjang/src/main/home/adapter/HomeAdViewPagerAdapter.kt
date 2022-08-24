package com.okre.bunjang.src.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okre.bunjang.databinding.ItemHomeAdViewPagerBinding

class HomeAdViewPagerAdapter(var list : MutableList<Int>) : RecyclerView.Adapter<HomeAdViewPagerAdapter.PagerViewHolder>() {
    inner class PagerViewHolder(val binding: ItemHomeAdViewPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(list : Int) {
            binding.itemHomeAdImage.setImageResource(list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = ItemHomeAdViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bindList(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}