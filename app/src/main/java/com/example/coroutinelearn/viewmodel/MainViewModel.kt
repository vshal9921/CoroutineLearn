package com.example.coroutinelearn.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(initialValue: Int) : ViewModel() {

    var counter = initialValue


    private val TAG = "Main"

    init {
        viewModelScope.launch(Dispatchers.IO) {
            while(true){
                delay(500)
                // Log.d(TAG, "viewModelScope coroutine running")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "viewModel cleared")
    }

}