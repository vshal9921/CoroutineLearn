package com.example.coroutinelearn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.coroutinelearn.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"
    private lateinit var mainViewModel: MainViewModel
    private lateinit var activityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun longRunningTask(){

        for (i in 0..1000000000L){
            var a = i
        }
    }

    override fun onResume() {
        super.onResume()

    }

    fun performTask(view: View) {

        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, " 1 - ${Thread.currentThread().name}")
            longRunningTask()
        }

        // associated with app lifecycle
        GlobalScope.launch (Dispatchers.Main){
            Log.d(TAG, " 2 - ${Thread.currentThread().name}")
        }

        // associated with activity lifecycleg
        MainScope().launch(Dispatchers.Default) {
            Log.d(TAG, " 3 - ${Thread.currentThread().name}")
        }

        lifecycleScope.launch(Dispatchers.IO) {

            while (true && isActive){
                delay(1000)
                Log.d(TAG, "lifeCycleScope in main running")
            }
        }
    }

    fun gotoNextScreen(view: View) {
        startActivity(Intent(this, SecondActivity::class.java))
        finish()
    }
}