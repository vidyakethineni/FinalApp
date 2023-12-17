package com.example.finalapp.ui.home.restaurantdetails.fooditems

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finalapp.MainViewModel
import com.example.finalapp.databinding.FragmentFoodItemsBinding
import com.example.finalapp.ui.home.restaurantdetails.RestaurantDetailsFragmentDirections
import com.example.finalapp.ui.home.restaurantdetails.fooditems.adapter.FoodItemsListAdapter

class FoodItemsFragment : Fragment() {

    companion object {
        fun newInstance() = FoodItemsFragment()
    }


    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentFoodItemsBinding
    private lateinit var adapter: FoodItemsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentFoodItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        adapter = FoodItemsListAdapter(onItemChanged = { foodItem->
           val updatedList = adapter.currentList.toMutableList()
            val index = updatedList.indexOfFirst { it.name == foodItem.name }
            if(index!=-1){
                updatedList[index] = foodItem
                adapter.submitList(updatedList.filter { (it.initialQty ?: 1) > 0 })
            }
        })
        binding.recyclerView.adapter = adapter
        viewModel.restaurant.observe(viewLifecycleOwner) {
            it?.apply {
                val foodItems = it.foodItems
                if(viewModel.checkedOutRestaurant.value == null){
                    adapter.submitList(foodItems)
                }else{
                    adapter.submitList(viewModel.checkedOutRestaurant.value?.foodItems)
                }
            }
        }


        binding.checkoutBtn.setOnClickListener{
            val currentList =  adapter.currentList.toMutableList()
            val checkoutRestaurant = viewModel.restaurant.value?.copy(
                foodItems = currentList
            )
            viewModel.setCheckedOutRestaurant(checkoutRestaurant)
            findNavController().navigate(RestaurantDetailsFragmentDirections.actionRestaurantDetailsFragmentToCheckoutFragment())
        }

    }

}