package com.example.finalapp.ui.recentorders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finalapp.MainViewModel
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentAllRestaurantsBinding
import com.example.finalapp.databinding.FragmentRecentOrdersBinding
import com.example.finalapp.models.LatLng
import com.example.finalapp.models.Order
import com.example.finalapp.ui.home.HomeFragmentDirections
import com.example.finalapp.ui.home.adapter.RestaurantAdapter
import com.example.finalapp.ui.home.checkout.CheckoutFragmentDirections
import com.example.finalapp.ui.recentorders.adapter.OrderAdapter

class RecentOrdersFragment : Fragment() {

    companion object {
        fun newInstance() = RecentOrdersFragment()
    }


    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentRecentOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentRecentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        val adapter = OrderAdapter(onReorderClicked = {
            order ->
            val newOrder = order.copy(
                dateTime =  System.currentTimeMillis().toString(),
            )
            viewModel.placeOrder(newOrder)

        })
        binding.recyclerView.adapter = adapter
        viewModel.allOrders.observe(viewLifecycleOwner) {
            it?.apply {
                adapter.submitList(this)
            }
        }
        viewModel.placedOrder.observe(viewLifecycleOwner){
            if(it!=null){
                findNavController().navigate(RecentOrdersFragmentDirections.actionNavRecentOrdersToOrderFragment())
            }
        }
    }

}