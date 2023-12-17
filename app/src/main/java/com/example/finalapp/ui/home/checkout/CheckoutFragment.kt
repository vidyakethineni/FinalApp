package com.example.finalapp.ui.home.checkout

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapp.MainViewModel
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentCheckoutBinding
import com.example.finalapp.models.LatLng
import com.example.finalapp.models.Order
import com.example.finalapp.ui.home.restaurantdetails.fooditems.adapter.FoodItemsListAdapter
import com.google.android.gms.location.LocationServices


class CheckoutFragment : Fragment() {

    companion object {
        fun newInstance() = CheckoutFragment()
    }


    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var adapter: FoodItemsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentCheckoutBinding.inflate(inflater, container, false)
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
                val list = updatedList.filter { (it.initialQty ?: 1) > 0 }
                adapter.submitList(list)
            }
        }, R.layout.list_item_checkout)
        binding.recyclerView.adapter = adapter
        viewModel.checkedOutRestaurant.observe(viewLifecycleOwner) {
            it?.apply {
                val foodItems = it.foodItems
                adapter.submitList(foodItems)
            }
        }
        val itemTouchHelper = ItemTouchHelper(object :ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
              return makeFlag(ACTION_STATE_SWIPE, LEFT )
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return  false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == LEFT) {
                    val position = viewHolder.adapterPosition
                    val updatedList = adapter.currentList.toMutableList()
                    updatedList.removeAt(position)
                    adapter.submitList(updatedList)
                }
            }

        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)


        binding.modifyOrderBtn.setOnClickListener {
            val currentList =  adapter.currentList.toMutableList()
            val checkoutRestaurant = viewModel.restaurant.value?.copy(
                foodItems = currentList
            )
            viewModel.setCheckedOutRestaurant(checkoutRestaurant)
            findNavController().navigateUp()
        }
        binding.placeOrderBtn.setOnClickListener {
            // Check for location permissions
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission already granted, proceed to fetch location
                fetchLocation()
            } else {
                // Request location permissions
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    100
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location
                if (location != null) {
                    // Use the location data (latitude, longitude, etc.)
                    val latitude = location.latitude

                    val dAddress = binding.deliveryAddressEt.text.toString()
                    val longitude = location.longitude
                    // Process the location data as needed
                    if(dAddress.isBlank()){
                        Toast.makeText(requireContext(), "Enter delivery Address!", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }
                    val splInst = binding.specialInsEt.text.toString()

                    val currentList =  adapter.currentList.toMutableList()
                    val order = Order(
                        currentList,
                        System.currentTimeMillis().toString(),
                        viewModel.restaurant.value?.name,
                        dAddress,
                        LatLng(latitude, longitude),
                        splInst
                    )
                    viewModel.placeOrder(order)
                }
            }
            .addOnFailureListener { e ->
                // Handle failure to get location
            }

        viewModel.placedOrder.observe(viewLifecycleOwner){
            if(it!=null){
                findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToOrderFragment())
            }
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, fetch location
                fetchLocation()
            } else {
                // Permission denied
                // Handle denial or show a message to the user
            }
        }
    }


}