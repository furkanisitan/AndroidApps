package com.furkanisitan.gradepointaverage

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

        ivBallon.animation = AnimationUtils.loadAnimation(this, R.anim.from_up_to_down)
        btnAvrCalAnim.animation = AnimationUtils.loadAnimation(this, R.anim.from_down_to_up)

        btnAvrCalAnim.setOnClickListener {
            ivBallon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.to_up))
            btnAvrCalAnim.startAnimation(AnimationUtils.loadAnimation(this, R.anim.to_down))

            // wait animation
            object : CountDownTimer(1000, 1000) {
                override fun onFinish() {
                    // MainActivity::class.java => MainActivity.class
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onTick(millisUntilFinished: Long) {}
            }.start()

        }
    }
}
