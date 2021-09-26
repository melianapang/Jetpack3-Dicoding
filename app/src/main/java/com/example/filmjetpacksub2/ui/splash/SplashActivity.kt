package com.example.filmjetpacksub2.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.filmjetpacksub2.R
import com.example.filmjetpacksub2.databinding.ActivitySplashBinding
import com.example.filmjetpacksub2.ui.home.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Animations
        binding.tvAppNameTitle.animation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        binding.logo.animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        binding.tvMotto.animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        //to go to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}