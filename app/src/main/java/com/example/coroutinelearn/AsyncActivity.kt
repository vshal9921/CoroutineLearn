package com.example.coroutinelearn

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coroutinelearn.databinding.ActivityAsyncBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AsyncActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAsyncBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAsyncBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun getFollowersAsync(view: View) {

        CoroutineScope(Dispatchers.IO).launch {
            callFollowerAPI()
        }
    }

    private suspend fun callFollowerAPI() {

        var fbFollowers = CoroutineScope(Dispatchers.IO).async {

            callFbApi()
        }
        var instaFollowers = CoroutineScope(Dispatchers.IO).async {

            callInstaApi()
        }

        withContext(Dispatchers.Main){
            binding.textFbFollowers.text = "FB = ${fbFollowers.await()}"
            binding.textInstaFollowers.text = "Insta = ${instaFollowers.await()}"
        }
    }

    // another approach of above using single scope
    // both tasks can be cancelled by cancelling launch job
    // Both the async blocks are executed in parallel in a single scope
    private suspend fun callFollowerAPI1() {

        CoroutineScope(Dispatchers.IO).launch {

            var fbFollowers = async { callFbApi() }
            var instaFollowers = async { callInstaApi() }

            withContext(Dispatchers.Main){
                binding.textFbFollowers.text = "FB = ${fbFollowers.await()}"
                binding.textInstaFollowers.text = "Insta = ${instaFollowers.await()}"
            }
        }
    }

    private suspend fun callFbApi() : Int {
        delay(2500)
        return 63
    }

    private suspend fun callInstaApi() : Int {
        delay(1000)
        return 101
    }
}