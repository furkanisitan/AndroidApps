package com.furkanisitan.animalpicturesapp

import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import kotlinx.android.synthetic.main.layout_animal.view.*

class AnimalAdapter(private var animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>(), Filterable {

    private val filterAnimal = FilterAnimalHelper(animalList, this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount(): Int = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) =
        holder.setData(animalList[position])

    override fun getFilter(): Filter = filterAnimal

    fun setFilter(list: ArrayList<Animal>) {
        animalList = list
    }

    /*
    fun setFilter(list: ArrayList<Animal>) {
        /* !!! Bu kısımda yeni instance üretilmeli, clear() metodu kullanılırsa; consructor'a MainActivity'den gelen
        değişkende de değişiiklik olur */
        animalList = ArrayList()
        animalList.addAll(list)
        notifyDataSetChanged()
    }
    */

    inner class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val view = itemView as CardView
        private var tvName = view.tvNameLayoutAnimal!!

        fun setData(animal: Animal) {
            tvName.text = animal.name
            tvName.setCompoundDrawablesWithIntrinsicBounds(0, animal.imageId, 0, 0)

            view.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra("tvText", animal.name)
                intent.putExtra("imgId", animal.imageId)
                it.context.startActivity(intent)
            }
        }
    }
}