package com.furkanisitan.myweightonotherplanets

import android.text.InputFilter
import android.text.Spanned

class InputFilterMax(val max: Double) : InputFilter {


    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        try {
            var input = (dest.toString() + source.toString()).toDouble()
            if (max >= input)
                return null
        } catch (nfe: NumberFormatException) {
        }
        return ""
    }
}