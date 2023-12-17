package com.example.finalapp.ui.home.restaurantdetails.imageslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalapp.MainViewModel
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentFoodItemsBinding
import com.example.finalapp.databinding.FragmentImagesListBinding
import com.example.finalapp.models.FoodItem
import com.example.finalapp.ui.home.restaurantdetails.fooditems.adapter.FoodItemsListAdapter
import com.example.finalapp.ui.home.restaurantdetails.imageslist.adapter.ImagesListAdapter

class ImagesListFragment : Fragment() {

    companion object {
        fun newInstance() = ImagesListFragment()
    }


    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentImagesListBinding
    private lateinit var adapter: ImagesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        adapter = ImagesListAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.restaurant.observe(viewLifecycleOwner) {
            it?.apply {
                adapter.submitList(this.imageUrls)
            }
        }
    }

}