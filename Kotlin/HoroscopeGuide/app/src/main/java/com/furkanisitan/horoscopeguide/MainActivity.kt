package com.furkanisitan.horoscopeguide

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.reflect.KMutableProperty1

class MainActivity : AppCompatActivity() {

    lateinit var horoscopes: ArrayList<Horoscope>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setHoroscopeList()

        // array adapter
        /*
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, horoscopes.listOfField(Horoscope::name))
        lvHoroscopes.adapter = adapter

        lvHoroscopes.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            view as TextView
            Toast.makeText(this@MainActivity, "${view.text}", Toast.LENGTH_SHORT).show()
        }
        */

        // array adapter (same image, different text)
        /*
        val adapter = ArrayAdapter(this, R.layout.partial_horoscope_simple, R.id.tvHoroscope,
                horoscopes.listOfField(Horoscope::name))
        lvHoroscopes.adapter = adapter

        lvHoroscopes.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            view as LinearLayout
            Toast.makeText(this@MainActivity, "${(view.getChildAt(1) as TextView).text}",
                    Toast.LENGTH_SHORT).show()
        }
        */

        // custom array adapter (different image, more than one TextView)
        /*
        val adapter = HoroscopeArrayAdapter(this, R.layout.partial_horoscope,
                R.id.tvNamePartialHoroscope,
                horoscopes.listOfField(Horoscope::name).toTypedArray(),
                horoscopes.listOfField(Horoscope::date).toTypedArray(),
                horoscopes.listOfField(Horoscope::imageId).toTypedArray())
        lvHoroscopes.adapter = adapter
        */

        // custom base adapter
        val adapter = HoroscopeBaseAdapter(this, R.layout.partial_horoscope, horoscopes)
        lvHoroscopes.adapter = adapter

        lvHoroscopes.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra("horoscope", horoscopes[position])
            startActivity(intent)
        }
    }

    private fun setHoroscopeList() {
        horoscopes = ArrayList()
        val horoscopeNames = resources.getStringArray(R.array.horoscopes)
        val horoscopeDates = resources.getStringArray(R.array.horoscopeDates)
        val horoscopeGeneralFeatures = resources.getStringArray(R.array.horoscopeGeneralFeatures)
        val horoscopeImageIDs = getIntegerArrayFromResources(R.array.horoscopeImageIDs)
        val horoscopeImageBigIDs = getIntegerArrayFromResources(R.array.horoscopeImageBigIDs)
        for (i in 0 until horoscopeNames.size)
            horoscopes.add(Horoscope(horoscopeNames[i], horoscopeDates[i], horoscopeGeneralFeatures[i],
                    horoscopeImageIDs[i], horoscopeImageBigIDs[i]))
    }

    private fun getIntegerArrayFromResources(id: Int): Array<Int> {
        val images = resources.obtainTypedArray(id)
        val array = Array(images.length()) { i -> images.getResourceId(i, -1) }
        images.recycle()
        return array
    }

    /*
    inline fun <reified T, Y> MutableList<T>.listOfField(property: KMutableProperty1<T, Y>): MutableList<Y> {
        val yy = ArrayList<Y>()
        this.forEach { t: T ->
            yy.add(property.get(t))
        }
        return yy
    }
    */
}
