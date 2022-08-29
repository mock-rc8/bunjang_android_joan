package com.okre.bunjang.src.main.home.buy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okre.bunjang.R
import com.okre.bunjang.databinding.ItemBuyDeliveryMethodOtherBinding
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryMethodOtherItem

class BuyDeliveryMethodOtherAdapter(val itemList : MutableList<BuyDeliveryMethodOtherItem>) : RecyclerView.Adapter<BuyDeliveryMethodOtherAdapter.ViewHolder>() {

    var selectPosition : Int = 0

    inner class ViewHolder(val binding: ItemBuyDeliveryMethodOtherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(item: BuyDeliveryMethodOtherItem) {
            binding.itemMethodOtherTitle.text = item.title
            if (selectPosition == adapterPosition) {
                binding.itemMethodOtherCheck.isChecked = true
                binding.itemMethodOtherLayout.setBackgroundResource(R.drawable.background_red_radius)
            } else {
                binding.itemMethodOtherCheck.isChecked = false
                binding.itemMethodOtherLayout.setBackgroundResource(R.drawable.background_btn_product_detail_event)
            }
            binding.itemMethodOtherLayout.setOnClickListener { v ->
                itemClick?.onClick(v, adapterPosition, item.title.toString())
                selectPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    interface ItemClick {
        fun onClick(view : View, position: Int, text: String)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBuyDeliveryMethodOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindList(itemList[position])

        when(position) {
            0 -> holder.binding.itemMethodOtherAdditional.visibility = View.VISIBLE
            else -> holder.binding.itemMethodOtherAdditional.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}