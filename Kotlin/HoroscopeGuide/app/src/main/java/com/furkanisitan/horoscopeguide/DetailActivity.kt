package com.furkanisitan.horoscopeguide

import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.graphics.Palette
import android.text.Html
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(anim_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)   // toolbar back button

        val horoscope = intent.getParcelableExtra<Horoscope>("horoscope")
        if (horoscope != null) {

            tvFeaturesDetailActivity.text = horoscope.generalFeatures

            header.setImageResource(horoscope.imageBigId)

            val bitmap = BitmapFactory.decodeResource(resources, horoscope.imageBigId)
            Palette.from(bitmap).generate {
                val color = it?.getVibrantColor(R.attr.colorAccent)
                color?.run {
                    collapsing_toolbar.setContentScrimColor(this)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        window.statusBarColor = color
                }
            }

            collapsing_toolbar.title = horoscope.name
        }
    }

    // toolbar back button pressed
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
