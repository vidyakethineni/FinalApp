package com.example.finalapp.ui.utils

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private val _isLoading = MutableLiveData(false)
    var isLoading: LiveData<Boolean> = _isLoading
    fun onLoadComplete() {
        _isLoading.postValue(false)
    }

    fun onLoadStart() {
        _isLoading.postValue(true)
    }
}