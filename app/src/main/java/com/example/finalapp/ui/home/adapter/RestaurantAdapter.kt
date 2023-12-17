package com.example.finalapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapp.R

class RestaurantAdapter(private val layoutId: Int, private val onItemClick: (String) -> Unit) :
    ListAdapter<String, RestaurantAdapter.ViewHolder>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }) {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtView = itemView.findViewById<TextView>(R.id.itemTv)
        fun bind(item: String?) {
            txtView.text = item ?: ""
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClick(getItem(holder.adapterPosition))
        }

    }
}