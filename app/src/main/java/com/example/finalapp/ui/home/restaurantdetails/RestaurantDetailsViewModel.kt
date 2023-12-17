package com.example.finalapp.ui.home.restaurantdetails

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalapp.FinalApplication
import com.example.finalapp.models.Restaurant
import com.example.finalapp.repository.FinalAppRepository
import com.example.finalapp.ui.utils.BaseViewModel

class RestaurantDetailsViewModel(application: Application) : BaseViewModel(application) {
    private val mRepository: FinalAppRepository
    private val _restaurant: MutableLiveData<Restaurant?> = MutableLiveData<Restaurant?>(null)
    var restaurant: LiveData<Restaurant?> = _restaurant

    init {
        mRepository = (application as FinalApplication).getRepository()
    }

    fun fetchRestaurant(name: String) {
        mRepository.fetchRestaurant(name, callBack = {
            it?.apply {
                if (it.isSuccess) {
                    _restaurant.postValue(it.getOrNull() as Restaurant?)
                }
            }
        })
    }


}