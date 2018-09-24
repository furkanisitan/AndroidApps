package com.furkanisitan.horoscopeguide

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.partial_horoscope.view.*

class HoroscopeArrayAdapter(private val mContext: Context, private val resource: Int,
                            private val textViewResourceId: Int, private val names: Array<String>,
                            private val dates: Array<String>, private val images: Array<Int>)
    : ArrayAdapter<String>(mContext, resource, textViewResourceId, names) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(mContext)
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else
            viewHolder = view.tag as ViewHolder

        viewHolder.name.text = names[position]
        viewHolder.date.text = dates[position]
        viewHolder.image.setImageResource(images[position])
        return view!!
    }

    class ViewHolder(view: View) {
        var name: TextView = view.tvNamePartialHoroscope
        var date: TextView = view.tvDatePartialHoroscope
        var image: ImageView = view.imvPartialHoroscope
    }
}