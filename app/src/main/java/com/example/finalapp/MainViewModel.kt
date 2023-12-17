package com.example.finalapp

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalapp.models.Order
import com.example.finalapp.models.Restaurant
import com.example.finalapp.models.Signup
import com.example.finalapp.models.User
import com.example.finalapp.repository.FinalAppRepository
import com.example.finalapp.ui.utils.BaseViewModel
/**
 * ViewModel class for the main functionality of the application.
 *
 * This ViewModel manages data related to the user, restaurants, orders, and selected restaurant.
 * It interacts with the repository to fetch and update data from the data source.
 */
class MainViewModel(application: Application) : BaseViewModel(application) {
    private val mRepository: FinalAppRepository
    private val _loggedInUser: MutableLiveData<User?> = MutableLiveData<User?>(null)
    var loggedInUser: LiveData<User?> = _loggedInUser

    private val _latestFiveOrders: MutableLiveData<List<Order>?> =
        MutableLiveData<List<Order>?>(null)
    var latestFiveOrders: LiveData<List<Order>?> = _latestFiveOrders

    private val _allRestaurants: MutableLiveData<List<Restaurant>?> =
        MutableLiveData<List<Restaurant>?>(null)
    var allRestaurants: LiveData<List<Restaurant>?> = _allRestaurants

    val selectedRestaurant = MutableLiveData<String?>(null)

    init {
        mRepository = (application as FinalApplication).getRepository()
        mRepository.fetchUserDetails() {
            it?.apply {
                if (it.isSuccess) {
                    _loggedInUser.postValue(it.getOrNull() as User?)
                }
            }
        }
        mRepository.uploadDummyDataIfNotAvailable()
        mRepository.fetchAllRestaurants(callBack = {
            it?.apply {
                if (it.isSuccess) {
                    _allRestaurants.postValue(it.getOrNull() as List<Restaurant>?)
                }
            }
        }, recentFiveRestaurants = {
            it?.apply {
                if (it.isSuccess) {
                    _latestFiveOrders.postValue(it.getOrNull() as List<Order>?)
                }
            }
        })
        fetchAllOrders()
    }

    private val _restaurant: MutableLiveData<Restaurant?> = MutableLiveData<Restaurant?>(null)
    var restaurant: LiveData<Restaurant?> = _restaurant
    fun setSelectedRestaurant(item: String?) {
        mRepository.fetchRestaurant(item?:return, callBack = {
            it?.apply {
                if (it.isSuccess) {
                    _restaurant.postValue(it.getOrNull() as Restaurant?)
                }
            }
        })
    }


    private val _checkedOutRestaurant: MutableLiveData<Restaurant?> = MutableLiveData<Restaurant?>(null)
    var checkedOutRestaurant: LiveData<Restaurant?> = _checkedOutRestaurant
    fun setCheckedOutRestaurant(restaurant: Restaurant?) {
        _checkedOutRestaurant.postValue(restaurant)
    }


    private val _placedOrder: MutableLiveData<Order?> = MutableLiveData<Order?>(null)
    var placedOrder: LiveData<Order?> = _placedOrder
    fun placeOrder(order: Order) {
        mRepository.placeOrder(order){
            it?.apply {
                if (it.isSuccess) {
                    _placedOrder.postValue(it.getOrNull() as Order?)
                }
            }
        }
    }

    private val _allOrders: MutableLiveData<List<Order>?> = MutableLiveData<List<Order>?>(null)
    var allOrders: LiveData<List<Order>?> = _allOrders
    fun fetchAllOrders() {
       mRepository.fetchAllOrders(){
           it?.apply {
               if (it.isSuccess) {
                   _allOrders.postValue(it.getOrNull() as List<Order>?)
               }
           }
       }
    }


}