package com.oratakashi.myflix.ui.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oratakashi.myflix.R
import com.oratakashi.myflix.databinding.ActivitySplashBinding
import com.oratakashi.myflix.ui.main.MainActivity
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.tools.startActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding: ActivitySplashBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lottie.addAnimatorUpdateListener {
                if(it.animatedFraction == 1.0f){
                    startActivity(MainActivity::class.java)
                    finish()
                }
            }
        }
    }
}