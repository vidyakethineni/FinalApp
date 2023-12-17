package com.example.finalapp.ui.login

import android.app.Application
import android.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalapp.FinalApplication
import com.example.finalapp.models.Signup
import com.example.finalapp.repository.FinalAppRepository
import com.example.finalapp.ui.utils.BaseViewModel
import com.example.finalapp.ui.utils.Utils

class LoginViewModel(application: Application) : BaseViewModel(application) {
    private val mRepository: FinalAppRepository

    init {
        mRepository = (application as FinalApplication).getRepository()
    }

    private val mSignupMutableLiveData: MutableLiveData<Signup> = MutableLiveData<Signup>(Signup())
    var mSignupLiveData: LiveData<Signup> = mSignupMutableLiveData
    fun isDetailsValidated(signup: Signup): Pair<Boolean, String> {
        if (!Utils.isValidEmail(signup.emailId)) {
            return Pair(false, "Enter a valid Email Id!")
        }
        return if (!Utils.isValidPassword(signup.password ?: "")) {
            Pair<Boolean, String>(false, "Enter a valid password")
        } else Pair<Boolean, String>(true, "Success")
    }


    private val resultMutableLiveData: MutableLiveData<Result<*>?> = MutableLiveData<Result<*>??>(null)
    var resultLiveData: LiveData<Result<*>?> = resultMutableLiveData


    fun performLogin(login: Signup?) {
        onLoadStart()
        mRepository.performLogin(login){
            onLoadComplete()
            resultMutableLiveData.postValue(it)
        }
    }

    fun resetResultLiveData() {
        resultMutableLiveData.value = null
    }





}