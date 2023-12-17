package com.example.finalapp.ui.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.finalapp.R
import com.example.finalapp.models.FoodItem
import com.example.finalapp.models.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@BindingAdapter("android:clickEnabled")
fun setClickEnabled(view: View, enabled: Boolean?) {
    if (enabled == null) {
        view.isClickable = true
        return
    }
    view.isClickable = enabled
}

@BindingAdapter("android:viewVisibility")
fun setVisibility(view: View, visible: Boolean?) {
    if (visible == null) {
        view.visibility = View.GONE
        return
    }
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}


@BindingAdapter("android:setImage")
fun ImageView.setImage(uri:String?){
    Glide
        .with(context)
        .load(uri)
        .centerCrop()
        .placeholder(R.drawable.baseline_account_circle_24)
        .into(this);
}


@BindingAdapter("android:setFoodItems")
fun TextView.setFoodItems(list:List<FoodItem>?){
    list?.let {
        val sb = StringBuilder()
        for(item in list){
            sb.append("${item.name}, ${item.initialQty}, $${item.price?.times(item.initialQty ?:1)}")
                .append("\n")
        }
        text = sb.toString()
    }
}


@BindingAdapter("android:setTotalPrice")
fun TextView.setTotalPrice(list:List<FoodItem>?){
    list?.let {
        var price:Int=0
        for(item in list){
            item?.let {
                price +=  (item.price?:0)*(item.initialQty?:1)
            }
        }
        text = "Total Price: $price"
    }
}

@BindingAdapter("android:setDateTime")
fun TextView.setDateTime(order: Order?){
    order?.let {
        val time =  order.dateTime?.toLong()?:System.currentTimeMillis()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate = sdf.format(Date(time))
        text = "Date and Time: $formattedDate"
    }
}