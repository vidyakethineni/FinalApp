package com.example.finalapp.ui.signup

import android.app.Application
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalapp.FinalApplication
import com.example.finalapp.models.Signup
import com.example.finalapp.repository.FinalAppRepository
import com.example.finalapp.ui.utils.BaseViewModel
import com.example.finalapp.ui.utils.Utils

class SignupViewModel(application: Application) : BaseViewModel(application) {
    private val mRepository: FinalAppRepository
    init {
        mRepository = (application as FinalApplication).getRepository()
    }
    private val mSignupMutableLiveData: MutableLiveData<Signup> = MutableLiveData<Signup>(Signup())
    var mSignupLiveData: LiveData<Signup> = mSignupMutableLiveData

    fun updateImageUri(uri: String?) {
        mSignupMutableLiveData.value = mSignupMutableLiveData.value?.copy(
            imageUrl = uri
        )
    }

    fun isDetailsValidated(signup: Signup): Pair<Boolean, String> {
        if (TextUtils.isEmpty(signup.fullName)) {
            return Pair(false, "Enter a valid full name!")
        }
        if (!Utils.isValidEmail(signup.emailId)) {
            return Pair(false, "Enter a valid Email Id!")
        }

        if (!Utils.isValidPassword(signup.password?:"")) {
            return Pair(false, "Enter a valid password")
        }
        if (signup.imageUrl.isNullOrBlank()) {
            return Pair(false, "Add profile image to proceed further!")
        }
        return Pair(true, "Success")
    }

    private val resultMutableLiveData: MutableLiveData<Result<*>?> = MutableLiveData<Result<*>??>(null)
    var resultLiveData: LiveData<Result<*>?> = resultMutableLiveData



    fun performSignupOnFirebase(signup:Signup) {
        onLoadStart()
        mRepository.performSignupOnFirebase(signup){
            onLoadComplete()
          resultMutableLiveData.postValue(it)
        }
    }

    fun resetResultLiveData() {
        resultMutableLiveData.value = null
    }
}