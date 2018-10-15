package com.furkanisitan.animalpicturesapp

import android.widget.Filter

class FilterAnimalHelper(private val list: ArrayList<Animal>, private val adapter: AnimalAdapter) : Filter() {

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val filteredList: ArrayList<Animal>

        if (!constraint.isNullOrEmpty()) {
            filteredList = ArrayList()
            list.filterTo(filteredList) { it.name.contains(constraint!!, true) }
        } else
            filteredList = list

        return FilterResults().apply {
            values = filteredList
            count = filteredList.size
        }
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        with(adapter) {
            setFilter(results?.values as ArrayList<Animal>)
            notifyDataSetChanged()
        }
    }
}