package com.example.finalapp.ui.home.restaurantdetails.fooditems.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapp.R
import com.example.finalapp.databinding.ListItemCheckoutBinding
import com.example.finalapp.databinding.ListItemFoodItemsBinding
import com.example.finalapp.models.FoodItem

class FoodItemsListAdapter(private val onItemChanged: (FoodItem) -> Unit,private val layoutId:Int=R.layout.list_item_food_items) :
    ListAdapter<FoodItem, FoodItemsListAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<FoodItem>() {
        override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
            return oldItem == newItem
        }

    }) {
    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: FoodItem?) {
            if(layoutId == R.layout.list_item_food_items){
                val binding =  DataBindingUtil.bind<ListItemFoodItemsBinding>(itemView)?:return
                binding.foodItem = item
                binding.executePendingBindings()
                binding.addImv.setOnClickListener {
                    item?.apply {
                        if ((item.initialQty ?: 1) < (item.maxQuantity ?: 4)){
                            val newItem = item.copy(initialQty = item.initialQty?.plus(1))
                            onItemChanged(newItem)
                        }

                    }
                }
                binding.decreaseImv.setOnClickListener {
                    item?.apply {
                        if ((item.initialQty ?: 1) >= 1) {
                            val newItem = item.copy(initialQty = item.initialQty?.minus(1))
                            onItemChanged( newItem)
                        }
                    }
                }
            }else{
                val binding = DataBindingUtil.bind<ListItemCheckoutBinding>(itemView)?:return
                binding.foodItem = item
                binding.executePendingBindings()
                binding.addImv.setOnClickListener {
                    item?.apply {
                        if ((item.initialQty ?: 1) < (item.maxQuantity ?: 4)){
                            val newItem = item.copy(initialQty = item.initialQty?.plus(1))
                            onItemChanged(newItem)
                        }

                    }
                }
                binding.decreaseImv.setOnClickListener {
                    item?.apply {
                        if ((item.initialQty ?: 1) >= 1) {
                            val newItem = item.copy(initialQty = item.initialQty?.minus(1))
                            onItemChanged( newItem)
                        }
                    }
                }
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodItemsListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodItemsListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}