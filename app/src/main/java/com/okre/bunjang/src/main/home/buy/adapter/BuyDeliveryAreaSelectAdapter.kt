package com.okre.bunjang.src.main.home.buy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okre.bunjang.databinding.ItemBuyDeliveryAreaSelectBinding
import com.okre.bunjang.src.main.home.buy.item.BuyDeliveryAreaSelectItem
import com.okre.bunjang.src.main.home.item.HomeRecommendItem

class BuyDeliveryAreaSelectAdapter() : RecyclerView.Adapter<BuyDeliveryAreaSelectAdapter.ViewHolder>() {

    private val itemList : MutableList<BuyDeliveryAreaSelectItem> = arrayListOf()

    inner class ViewHolder(val binding: ItemBuyDeliveryAreaSelectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindList(item : BuyDeliveryAreaSelectItem) {
            binding.itemAreaSelectAddress.text = item.address + item.detailAddress
            binding.itemAreaSelectName.text = item.name
            binding.itemAreaSelectPhone.text = item.phone
            binding.itemAreaSelectLayout.setOnClickListener { v ->
                itemClick?.onClick(v, adapterPosition, item.address, item.detailAddress, item.name, item.phone)
            }
        }
    }

    interface ItemClick {
        fun onClick(view : View, position: Int, address: String, detailAddress: String, name: String, phone: String)
    }
    var itemClick : ItemClick? = null

    fun addList(buyDeliveryAreaSelectItem: BuyDeliveryAreaSelectItem) {
        itemList.add(buyDeliveryAreaSelectItem)
        notifyItemInserted(itemList.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  ItemBuyDeliveryAreaSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindList(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}