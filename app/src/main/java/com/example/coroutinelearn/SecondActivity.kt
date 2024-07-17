package com.example.coroutinelearn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var secondViewModel: SecondViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        secondViewModel = ViewModelProvider(this).get(SecondViewModel::class.java)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        secondViewModel.fbFollowers.observe(this, Observer { follower ->
            binding.textFbFollowers.text = "FB = $follower"
        })

        secondViewModel.instaFollowers.observe(this, Observer { follower ->
            binding.textInstaFollowers.text = "Insta = ${follower}"
        })
    }

    fun getFollowers(view: View) {
        secondViewModel.callFollowersAPI()
    }
    fun gotoNext(view: View) {
        startActivity(Intent(this, AsyncActivity::class.java))
    }
}