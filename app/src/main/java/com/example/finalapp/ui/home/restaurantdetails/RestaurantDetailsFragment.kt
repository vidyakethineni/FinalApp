package com.example.finalapp.ui.home.restaurantdetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.finalapp.MainViewModel
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentFavouriteRestaurantsBinding
import com.example.finalapp.databinding.FragmentRestaurantDetailsBinding
import com.example.finalapp.ui.home.adapter.RestaurantAdapter

class RestaurantDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = RestaurantDetailsFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentRestaurantDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.restaurant.observe(viewLifecycleOwner) {
            it?.apply {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}