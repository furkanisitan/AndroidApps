package com.furkanisitan.animalpicturesapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() /*,SearchView.OnQueryTextListener*/ {

    private val animalList = ArrayList<Animal>()
    private lateinit var adapter: AnimalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animalList.addAll(getAnimalList())
        adapter = AnimalAdapter(animalList)

        rcvMainActivity.adapter = adapter
        rcvMainActivity.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        svMainActivity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false    // listener tarafından gerçekleştirilmediği için false döndürülmelidir.
            }
        })
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter, menu)

        val menuItem = menu?.findItem(R.id.app_bar_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    // Enter'a basıldığında çalışır
    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        val filteredList = ArrayList<Animal>()
        animalList.filterTo(filteredList) { it.name.contains(newText!!, true) }
        adapter.setFilter(filteredList)
        return true
    }
    */

    private fun getAnimalList(): ArrayList<Animal> {
        val arrayList = ArrayList<Animal>()
        val imageIDs = getIntegerArrayFromResources(R.array.animalImageIDs)
        val names = resources.getStringArray(R.array.animalNames)
        for (i in 0 until names.size)
            arrayList.add(Animal(names[i], imageIDs[i]))
        return arrayList
    }

    private fun getIntegerArrayFromResources(id: Int): Array<Int> {
        val images = resources.obtainTypedArray(id)
        val array = Array(images.length()) { i -> images.getResourceId(i, -1) }
        images.recycle()
        return array
    }
}
