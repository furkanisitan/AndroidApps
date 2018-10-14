package com.furkanisitan.landscapeapp

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_landscape.view.*

class LandscapeAdapter(private val landscapeList: ArrayList<Landscape>) :
        RecyclerView.Adapter<LandscapeAdapter.LandscapeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandscapeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_landscape, parent, false)
        return LandscapeViewHolder(view)
    }

    override fun getItemCount(): Int = landscapeList.size

    override fun onBindViewHolder(holder: LandscapeViewHolder, position: Int) =
            holder.setData(landscapeList[position], position)

    inner class LandscapeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val view = itemView as CardView
        private val btnDelete = view.imgDeleteLayoutLandscape
        private val btnCopy = view.imgCopyLayoutLandscape
        var tvTitle = view.tvTitleLayoutLandscape!!
        var tvDescription = view.tvDescriptionLayoutLandscape!!
        var imgLandscape = view.imgLandscapeLayoutLandscape!!

        fun setData(landscape: Landscape, position: Int) {
            tvTitle.text = landscape.title
            tvDescription.text = landscape.description
            imgLandscape.setImageResource(landscape.imageId)

            btnDelete.setOnClickListener {
                landscapeList.removeAt(position)
                notifyItemRemoved(position)         // ilgili view'i recyclerView'dan siler
                notifyItemRangeChanged(position, landscapeList.size) // position'ları günceller
            }

            btnCopy.setOnClickListener {
                landscapeList.add(position, landscape)
                notifyItemInserted(position)        // ilgili view'i recyclerView'a ekler
                notifyItemRangeChanged(position, landscapeList.size)
            }
        }
    }
}