package com.example.finalapp.ui.home.favouriterestaurants

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
import com.example.finalapp.databinding.FragmentAllRestaurantsBinding
import com.example.finalapp.databinding.FragmentFavouriteRestaurantsBinding
import com.example.finalapp.ui.home.HomeFragmentDirections
import com.example.finalapp.ui.home.adapter.RestaurantAdapter

class FavouriteRestaurantsFragment : Fragment() {

    companion object {
        fun newInstance() = FavouriteRestaurantsFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentFavouriteRestaurantsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        val adapter = RestaurantAdapter(R.layout.list_item_fav_restaurant, onItemClick = {
            viewModel.setSelectedRestaurant(it)
            findNavController().navigate(HomeFragmentDirections.actionNavHomeToRestaurantDetailsFragment())
        })
        binding.recyclerView.adapter = adapter
        viewModel.latestFiveOrders.observe(viewLifecycleOwner) {
            it?.apply {
                val namesList = it.map { a -> a.restaurantName }.distinct()
                adapter.submitList(namesList)
            }
        }
    }

}