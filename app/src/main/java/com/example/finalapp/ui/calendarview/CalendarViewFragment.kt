package com.example.finalapp.ui.calendarview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.finalapp.MainViewModel
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentCalendarViewBinding
import com.example.finalapp.databinding.FragmentRecentOrdersBinding
import com.example.finalapp.models.FoodItem
import com.example.finalapp.models.Order
import com.example.finalapp.ui.recentorders.RecentOrdersFragmentDirections
import com.example.finalapp.ui.recentorders.adapter.OrderAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarViewFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarViewFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentCalendarViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentCalendarViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        var datesList:List<String> = mutableListOf()
        viewModel.allOrders.observe(viewLifecycleOwner) {
            it?.apply {
                // Example list of dates in milliseconds
                 datesList = this.map { item->getDateFromMillis(item.dateTime?.toLong()?:System.currentTimeMillis()) } // Replace with your list of dates
                    // Add more dates here as needed
            }
        }

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val dateInMillis = calendar.timeInMillis

            if (datesList.contains(getDateFromMillis(dateInMillis))) {
                // Date is in the highlight list, allow selection
                // Do your logic here on date selection if needed
                var totalSpend = fetchTotalSpent(getDateFromMillis(dateInMillis), viewModel.allOrders.value?: mutableListOf())
                Toast.makeText(requireContext(), "Total Spend: $$totalSpend", Toast.LENGTH_SHORT).show()
            } else {
                // Date is not in the highlight list, prevent selection
                view.setDate(System.currentTimeMillis()) // Reset calendar to current date
                Toast.makeText(requireContext(), "No order found!", Toast.LENGTH_SHORT).show()
            }
        }


















    }


    fun fetchTotalSpent(dateTime:String,orderList:List<Order>):Int{
        var totalPrice = 0;
        for (item in orderList){
            if(getDateFromMillis(item.dateTime?.toLong()?:System.currentTimeMillis())==dateTime){
                totalPrice += fetchTotalPrice(item.foodItems)
            }
        }
        return totalPrice
    }

    fun getDateFromMillis(timeInMillis: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        return dateFormat.format(calendar.time)
    }

    fun fetchTotalPrice(list:List<FoodItem>?):Int{
        list?.let {
            var price:Int=0
            for(item in list){
                item?.let {
                    price +=  (item.price?:0)*(item.initialQty?:1)
                }
            }
          return price
        }
        return 0
    }


}