package com.example.coroutinelearn

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coroutinelearn.databinding.ActivitySecondBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Example of launch{} coroutine builder

class SecondActivity : AppCompatActivity() {

    private val TAG = "SecondActivity"
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun getFollowers(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            callFollowersAPI()
        }
    }

    private suspend fun callFollowersAPI(){

        var fbFollowers = 0
        var instaFollowers = 0

        // Both the FB and Insta coroutines run in parallel

        val jobFb = CoroutineScope(Dispatchers.IO).launch {
            fbFollowers = getFbFollowers()
            Log.d(TAG, "FB followers = ${fbFollowers}")   // executed after getFbFollowers, returns 72
        }

        val jobInsta = CoroutineScope(Dispatchers.IO).launch {
            instaFollowers = getInstaFollowers()

            Log.d(TAG, "FB followers = ${instaFollowers}")
        }

        // To publish results after both coroutines have been executed, use join()

        jobFb.join()
        jobInsta.join()
        Log.d(TAG, "Thread = ${Thread.currentThread().name}")

        withContext(Dispatchers.Main){
            binding.textFbFollowers.text = "FB = $fbFollowers"
            binding.textInstaFollowers.text = "Insta = $instaFollowers"
            Log.d(TAG, "Thread = ${Thread.currentThread().name}")
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