package com.aruna.kliknelayanwakatobi.customui.form

import android.app.DatePickerDialog
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import com.aruna.kliknelayanwakatobi.R
import java.util.*

/**
 * Created by marzellaalfamega on 12/5/17.
 */
class FormSimple(context: Context, title: String, inputType: Int = InputType.TYPE_CLASS_TEXT) : FormBase(context, title),
        DatePickerDialog.OnDateSetListener {

    var edtValue: EditText
    private var datePickerDialog: DatePickerDialog? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.form_simple, null, false)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitleFormSimple)
        tvTitle.text = title


        edtValue = view.findViewById(R.id.edtValueFormSimple)
        if (inputType == InputType.TYPE_CLASS_DATETIME) {
            edtValue.inputType = InputType.TYPE_NULL
            edtValue.isFocusable = false
            edtValue.isClickable = true
            val calendar = Calendar.getInstance()
            datePickerDialog = DatePickerDialog(
                    context, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            edtValue.setOnClickListener { openCalendar() }
        } else {
            edtValue.inputType = inputType
        }
        addView(view)
    }

    private fun openCalendar() {
        datePickerDialog?.show()
    }

    override fun getValue(): String {
        return edtValue.text.toString()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        val date = "$year-${month + 1}-$day"
        edtValue.setText(date)
    }

}