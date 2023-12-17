package com.example.finalapp.ui.home.order

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finalapp.MainViewModel
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentFoodItemsBinding
import com.example.finalapp.databinding.FragmentOrderBinding
import com.example.finalapp.ui.home.restaurantdetails.RestaurantDetailsFragmentDirections
import com.example.finalapp.ui.home.restaurantdetails.fooditems.adapter.FoodItemsListAdapter

class OrderFragment : Fragment() {

    companion object {
        fun newInstance() = OrderFragment()
    }


    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentOrderBinding
    private lateinit var adapter: FoodItemsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        binding.lifecycleOwner = this
        viewModel.placedOrder.observe(viewLifecycleOwner) {
            it?.apply {
                binding.order = it
            }
        }
        binding.trackBtn.setOnClickListener {

        }
    }

}