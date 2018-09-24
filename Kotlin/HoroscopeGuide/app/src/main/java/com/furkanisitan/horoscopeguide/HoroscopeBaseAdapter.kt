package com.furkanisitan.horoscopeguide

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.partial_horoscope.view.*
import java.util.ArrayList

class HoroscopeBaseAdapter(private val context: Context, private val resource: Int,
                           private val horoscopes: ArrayList<Horoscope>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else
            viewHolder = view.tag as ViewHolder

        viewHolder.name.text = horoscopes[position].name
        viewHolder.date.text = horoscopes[position].date
        viewHolder.imageId.setImageResource(horoscopes[position].imageId)
        return view!!
    }

    override fun getItem(position: Int): Any = horoscopes[position]

    override fun getItemId(position: Int): Long = 0 // for database operations (no need to coding now)

    override fun getCount(): Int = horoscopes.size

    class ViewHolder(view: View) {
        var name: TextView = view.tvNamePartialHoroscope
        var date: TextView = view.tvDatePartialHoroscope
        var imageId: ImageView = view.imvPartialHoroscope
    }
}
