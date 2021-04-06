package com.ilham.githubmemberapp.ui.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilham.githubmemberapp.databinding.ActivitySplashBinding
import com.ilham.githubmemberapp.ui.view.main.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            delay(5000L)
            moveIntent()
        }
    }
    private fun moveIntent(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}