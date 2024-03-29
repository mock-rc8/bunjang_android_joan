package com.okre.bunjang.src.main.home.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.okre.bunjang.databinding.ItemHomeRecommendBinding
import com.okre.bunjang.src.main.home.HomeRecommendProductDetailActivity
import com.okre.bunjang.src.main.home.item.HomeRecommendItem

class HomeRecommendAdapter(val itemList : MutableList<HomeRecommendItem>) : RecyclerView.Adapter<HomeRecommendAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemHomeRecommendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(item : HomeRecommendItem) {
            Glide.with(binding.itemHomeRecommendImageview.context)
                .load(item.recommendImage)
                .into(binding.itemHomeRecommendImageview)
            binding.itemHomeRecommendCheckbox.isChecked = item.recommendCheckbox

            binding.itemHomeRecommendCheckbox.setOnClickListener { v ->
                item.recommendCheckbox = !item.recommendCheckbox
                if (binding.itemHomeRecommendCheckbox.isChecked) {
                    item.recommendHeartCount++
                } else {
                    item.recommendHeartCount--
                }
                itemClick?.onClick(v, adapterPosition, item.recommendIdx, item.recommendCheckbox)
                notifyDataSetChanged()
            }
            binding.itemHomeRecommendPrice.text = item.recommendPrice
            binding.itemHomeRecommendProductName.text = item.recommendProductName
            binding.itemHomeRecommendLocation.text = item.recommendLocation
            if (item.recommendTime.isNullOrEmpty()) {
                binding.itemHomeRecommendDivider.visibility = View.INVISIBLE
            } else {
                binding.itemHomeRecommendDivider.visibility = View.VISIBLE
            }
            binding.itemHomeRecommendTime.text = item.recommendTime
            if (item.recommendLightningPay) {
                binding.itemHomeRecommendLightningPay.visibility = View.VISIBLE
            } else {
                binding.itemHomeRecommendLightningPay.visibility = View.GONE
            }
            if (item.recommendHeartCount == 0) {
                binding.itemHomeRecommendHeart.visibility = View.GONE
            } else {
                binding.itemHomeRecommendHeart.visibility = View.VISIBLE
                binding.itemHomeRecommendHeart.text = item.recommendHeartCount.toString()
            }

            binding.itemHomeRecommendLayout.setOnClickListener { v ->
                val content = binding.itemHomeRecommendLayout.context
                val intent = Intent(content, HomeRecommendProductDetailActivity::class.java)
                intent.putExtra("productIdx", item.recommendIdx)
                content.startActivity(intent)
            }
        }
    }

    interface ItemClick {
        fun onClick(view : View, position: Int, productIdx: Int, checkbox: Boolean)
    }
    var itemClick : ItemClick? = null

    fun addList(homeRecommendItem: HomeRecommendItem) {
        itemList.add(homeRecommendItem)
        notifyItemInserted(itemList.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHomeRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindList(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}