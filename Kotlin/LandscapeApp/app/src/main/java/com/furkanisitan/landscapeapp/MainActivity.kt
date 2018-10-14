package com.furkanisitan.landscapeapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcvMainActivity.adapter = LandscapeAdapter(getLandscapeList())

        // recyclerView'a adapter dışında bir de layoutManager atanmalı
        // context, orientation(default=1(vertical)), reverseLayout(default=false)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcvMainActivity.layoutManager = linearLayoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val layoutManager: RecyclerView.LayoutManager? = when (item?.itemId) {
            R.id.mnuLinearVerticalMenuMain -> LinearLayoutManager(this)
            R.id.mnuLinearHorizontalMenuMain -> LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            R.id.mnuGridMenuMain -> GridLayoutManager(this, 2)
            R.id.mnuStaggeredGridVerticalMenuMain -> StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            R.id.mnuStaggeredGridHorizontalMenuMain -> StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
            else -> null
        }
        layoutManager?.run { rcvMainActivity.layoutManager = this }
        return super.onOptionsItemSelected(item)
    }


    private fun getLandscapeList(): ArrayList<Landscape> {
        val arrayList = ArrayList<Landscape>()
        val imageIDs = getIntegerArrayFromResources(R.array.landscapeImageIDs)
        imageIDs.forEachIndexed { index, item ->
            arrayList.add(Landscape("Başlık $index", "Açıklama $index", item))
        }
        return arrayList
    }

    private fun getIntegerArrayFromResources(id: Int): Array<Int> {
        val images = resources.obtainTypedArray(id)
        val array = Array(images.length()) { i -> images.getResourceId(i, -1) }
        images.recycle()
        return array
    }
}
