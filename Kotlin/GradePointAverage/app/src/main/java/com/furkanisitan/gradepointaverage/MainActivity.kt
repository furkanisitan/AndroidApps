package com.furkanisitan.gradepointaverage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_lesson_layout.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val _lessonNames = arrayOf("Matematik", "Algoritmalar", "Fizik", "Veri Yapıları", "Web Programlama")
    private val _points = mapOf("AA" to 4.0, "BA" to 3.5, "BB" to 3.0, "CB" to 2.5, "CC" to 2.0, "DC" to 1.5, "DD" to 1.0, "FF" to .0)
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // lesson names array adapter
        adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, _lessonNames)

        etLessonName.setAdapter(adapter)

        // btnCalculate visibility
        rootLayout.setOnHierarchyChangeListener(object : ViewGroup.OnHierarchyChangeListener {
            override fun onChildViewRemoved(parent: View?, child: View?) = setVisibilityOfBtnCalculate()
            override fun onChildViewAdded(parent: View?, child: View?) = setVisibilityOfBtnCalculate()
        })

        // lesson add listener
        btnAddLesson.setOnClickListener {

            if (etLessonName.text.isNullOrEmpty()) {
                FancyToast.makeText(this, "Ders Adını Giriniz"
                        , FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                return@setOnClickListener
            }

            val lesson = Lesson(etLessonName.text.toString(), spnLessonCredit.selectedItemPosition,
                    spnLessonPoint.selectedItemPosition)
            addNewLessonToRootLayout(lesson)
        }
    }

    fun btnCalculateClick(view: View) {

        var sumPoint = 0.0
        var sumCredit = 0
        for (i in 0 until rootLayout.childCount) {
            val lesson = rootLayout.getChildAt(i)
            val credit = lesson.spnNewLessonCredit.selectedItemPosition + 1
            sumPoint += _points[lesson.spnNewLessonPoint.selectedItem]!! * credit
            sumCredit += credit
        }
        FancyToast.makeText(this, "ORTALAMA: ${(sumPoint / sumCredit).round(2)}"
                , FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show()
    }

    private fun addNewLessonToRootLayout(lesson: Lesson) {
        val inflater = LayoutInflater.from(this)
        // var inflater2 = layoutInflater
        // var inflater3 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val newLessonView = inflater.inflate(R.layout.new_lesson_layout, null)

        newLessonView.etNewLessonName.setText(lesson.name)
        newLessonView.spnNewLessonCredit.setSelection(lesson.creditPosition)
        newLessonView.spnNewLessonPoint.setSelection(lesson.pointPosition)

        newLessonView.etNewLessonName.setAdapter(adapter)

        // lesson delete listener
        newLessonView.btnRemoveLesson.setOnClickListener {
            rootLayout.removeView(newLessonView)
        }

        rootLayout.addView(newLessonView)
        reset()
    }

    private fun getListOfAddedLessons(): ArrayList<Lesson> {
        val list = ArrayList<Lesson>()
        for (i in 0 until rootLayout.childCount) {
            val item = rootLayout.getChildAt(i)
            val lesson = Lesson(item.etNewLessonName.text.toString(),
                    item.spnNewLessonCredit.selectedItemPosition,
                    item.spnNewLessonPoint.selectedItemPosition)
            list.add(lesson)
        }
        return list
    }

    private fun setVisibilityOfBtnCalculate() {
        btnCalculate.visibility = if (rootLayout.childCount == 0) View.INVISIBLE else View.VISIBLE
    }

    private fun reset() {
        etLessonName.text.clear()
        spnLessonCredit.setSelection(0)
        spnLessonPoint.setSelection(0)
    }

    private fun Double.round(decimalPlace: Int): Double {
        val multiple = Math.pow(10.0, decimalPlace.toDouble())
        val num = this + 5 / (multiple * 10)
        return Math.floor(num * multiple) / multiple
    }

}
