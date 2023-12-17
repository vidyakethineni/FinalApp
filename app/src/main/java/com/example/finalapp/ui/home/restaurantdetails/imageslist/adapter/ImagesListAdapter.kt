package com.example.finalapp.ui.home.restaurantdetails.imageslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapp.R
import com.example.finalapp.databinding.ListItemImageListBinding

class ImagesListAdapter : ListAdapter<String, ImagesListAdapter.ViewHolder>(object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}) {
    inner class ViewHolder(private val binding: ListItemImageListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String?) {
            binding.imageUrl = item
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagesListAdapter.ViewHolder {
        val binding:ListItemImageListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.list_item_image_list, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}