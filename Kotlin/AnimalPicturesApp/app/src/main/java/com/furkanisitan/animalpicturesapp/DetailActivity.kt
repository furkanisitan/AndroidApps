package com.furkanisitan.animalpicturesapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = intent
        if (intent != null) {
            tvDetailActivity.text = intent.getStringExtra("tvText")
            imgDetailActivity.setImageResource(intent.getIntExtra("imgId", R.drawable.ani_cat_one))
        }
    }
}
