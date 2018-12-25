package com.furkanisitan.weatherapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Response
import com.furkanisitan.weatherapp.helpers.OpenWeatherMapHelper
import com.furkanisitan.weatherapp.helpers.TimeHelper
import com.furkanisitan.weatherapp.helpers.VolleyHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var oWMHelper: OpenWeatherMapHelper? = null
    private lateinit var volleyHelper: VolleyHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        volleyHelper = VolleyHelper(this)

        tvDate_Main.text = TimeHelper.getCurrentDate()
        spn_Main.setTitle("Şehir Seçin")
        setAdapterToSpnMain()
        registerHandlers()
    }

    private fun registerHandlers() {
        onItemSelected()
    }

    private fun onItemSelected() {
        spn_Main.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val city = parent?.getItemAtPosition(position)?.toString()
                displayInfo(city)
            }
        }
    }

    private fun setAdapterToSpnMain() {
        ArrayAdapter.createFromResource(
            this, R.array.city_list,
            R.layout.partial_simple_spinner_list_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spn_Main.adapter = adapter
        }
    }

    private fun displayInfo(city: String?) {
        city?.let { cityName ->
            volleyHelper.get(
                OpenWeatherMapHelper.getUrl(cityName),
                Response.Listener { response ->
                    oWMHelper = response?.let { OpenWeatherMapHelper.getInstance(response) }
                    if (oWMHelper != null)
                        setViews()
                },
                Response.ErrorListener {
                    Toast.makeText(this, "err : network connection", Toast.LENGTH_LONG).show()
                })
        }
    }

    private fun setViews() {
        oWMHelper?.let { helper ->
            tvTemp_Main.text = helper.getTemp().toString()
            tvDesc_Main.text = helper.getDesc().toString()
            helper.getIconName()?.let { iconName ->
                setIcon(iconName)
                setBackground()
            }
        }
    }

    private fun setIcon(iconName: String) {
        val identifier = resources.getIdentifier("icon_${iconName.dropLast(1)}", "drawable", packageName)
        img_Main.setImageResource(identifier)
    }

    private fun setBackground() {
        oWMHelper?.let { helper ->
            when (helper.isNight()) {
                true -> rootLayout_Main.background = getDrawable(R.drawable.bg_night)
                false -> rootLayout_Main.background = getDrawable(R.drawable.bg)
            }
        }
    }
}
