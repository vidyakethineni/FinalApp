package com.example.finalapp.ui.home.allrestaurants

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
import com.example.finalapp.ui.home.HomeFragmentDirections
import com.example.finalapp.ui.home.adapter.RestaurantAdapter

class AllRestaurantsFragment : Fragment() {

    companion object {
        fun newInstance() = AllRestaurantsFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding:FragmentAllRestaurantsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentAllRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        val adapter = RestaurantAdapter(R.layout.list_item_all_restaurant, onItemClick = {
            viewModel.setSelectedRestaurant(it)
            findNavController().navigate(HomeFragmentDirections.actionNavHomeToRestaurantDetailsFragment())
        })
        binding.recyclerView.adapter = adapter
        viewModel.allRestaurants.observe(viewLifecycleOwner) {
            it?.apply {
                val namesList = it.map { a -> a.name }
                adapter.submitList(namesList)
            }
        }
    }

}