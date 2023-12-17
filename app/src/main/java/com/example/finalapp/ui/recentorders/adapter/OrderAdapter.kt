package com.example.finalapp.ui.recentorders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapp.databinding.ListItemRecentOrderBinding
import com.example.finalapp.models.Order

class OrderAdapter(val onReorderClicked:(Order)->Unit) :
    ListAdapter<Order, OrderAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.dateTime == newItem.dateTime
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

    }) {
    class ViewHolder(val binding: ListItemRecentOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Order?) {
            binding.order = item
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemRecentOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.reOrderBtn.setOnClickListener {
            onReorderClicked(getItem(holder.adapterPosition))
        }
    }
}