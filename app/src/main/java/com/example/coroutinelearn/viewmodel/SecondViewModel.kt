package com.example.coroutinelearn.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SecondViewModel: ViewModel() {

    private val TAG = "SecondViewModel"
    val fbFollowers = MutableLiveData<Int>()
    val instaFollowers = MutableLiveData<Int>()

    fun callFollowersAPI(){

        // Both the FB and Insta coroutines run in parallel

        viewModelScope.launch(Dispatchers.IO) {

            val fbCount = async {
                getFbFollowers()
            }

            val instaCount = async {
                getInstaFollowers()
            }

            fbFollowers.postValue(fbCount.await())
            instaFollowers.postValue(instaCount.await())

            Log.d(TAG, "FB followers = ${fbCount}")   // executed after getFbFollowers, returns 72}
            Log.d(TAG, "FB followers = ${instaCount}")
        }
    }

    private suspend fun getFbFollowers() : Int {
        delay(2000)
        return 72
    }

    private suspend fun getInstaFollowers() : Int{
        delay(1000)
        return 56
    }

}