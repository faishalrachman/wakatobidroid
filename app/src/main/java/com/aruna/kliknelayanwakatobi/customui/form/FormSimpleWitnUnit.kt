package com.aruna.kliknelayanwakatobi.customui.form

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.aruna.kliknelayanwakatobi.R

/**
 * Created by marzellaalfamega on 12/5/17.
 */
class FormSimpleWitnUnit(context: Context, title: String, unit: String) : FormBase(context, title) {

    var edtValue: EditText

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.form_simple_with_unit, null, false)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitleFormSimpleWithUnit)
        tvTitle.text = title

        val tvSatuan = view.findViewById<TextView>(R.id.tvSatuanFormSimpleWithUnit)
        tvSatuan.text = unit

        edtValue = view.findViewById(R.id.edtValueFormSimpleWithUnit)

        addView(view)
    }

    override fun getValue(): Double {
        return edtValue.text.toString().toDouble()
    }

}