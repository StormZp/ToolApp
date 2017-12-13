package com.storm.toolapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.storm.toolapp.R
import com.storm.toolapp.base.BaseActivity
import com.storm.toolapp.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(R.layout.activity_splash)
        binding.activitySplash.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun initData() {
        val aa = AlphaAnimation(0.3f, 1.0f)
        aa.duration = 2000
        binding.activitySplash.startAnimation(aa)
        aa.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                startActivity(Intent(activity, MainActivity::class.java))
            }

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationStart(animation: Animation) {}

        })
    }


    override fun initTitle() {

    }

    override fun initListener() {

    }
}
