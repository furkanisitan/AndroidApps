package com.furkanisitan.myweightonotherplanets

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var checkedCheckBox: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setChecboxListeners()
        etWeight.filters = arrayOf(InputFilterMax(1000.0))
        etWeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                    setNewResult(checkedCheckBox?.id)
        })
    }

    private fun setChecboxListeners() {
        cbMercury.setOnClickListener(this)
        cbVenus.setOnClickListener(this)
        cbMars.setOnClickListener(this)
        cbJupiter.setOnClickListener(this)
        cbSaturn.setOnClickListener(this)
        cbUranus.setOnClickListener(this)
        cbNeptune.setOnClickListener(this)
        cbPluto.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v as CheckBox
        checkedCheckBox?.isChecked = false
        checkedCheckBox = v
        setNewResult(v.id)
    }

    fun setNewResult(checkedId: Int?) {

        tvResult.text = if (etWeight.text.isNotEmpty() && checkedId != null)
            RadioButtonInvoker(etWeight.text.toString().toDouble()).invoke(checkedId)
                    .format(2).toString()
        else null
    }

    private fun Double.format(count: Int) = java.lang.String.format("%.${count}f", this)
}