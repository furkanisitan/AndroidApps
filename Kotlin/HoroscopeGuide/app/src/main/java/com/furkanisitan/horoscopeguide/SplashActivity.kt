package com.furkanisitan.horoscopeguide

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animRotateLogo = AnimationUtils.loadAnimation(this, R.anim.rotate_logo)
        imvLogoSplashActivity.animation = animRotateLogo
    }

    override fun onResume() {

        object : CountDownTimer(5000, 1000) {
            override fun onFinish() =
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))

            override fun onTick(millisUntilFinished: Long) {}
        }.start()

        super.onResume()
    }
}
