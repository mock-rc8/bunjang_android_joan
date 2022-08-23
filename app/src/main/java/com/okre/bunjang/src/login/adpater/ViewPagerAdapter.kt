package com.okre.bunjang.src.login.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okre.bunjang.databinding.ItemViewPagerLoginBinding


class ViewPagerAdapter(var list : MutableList<Int>) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(val binding: ItemViewPagerLoginBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(list : Int) {
            binding.itemViewPagerImage.setImageResource(list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = ItemViewPagerLoginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bindList(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}