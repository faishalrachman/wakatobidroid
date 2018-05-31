package com.aruna.kliknelayanwakatobi.customui.form

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import com.aruna.kliknelayanwakatobi.R
import com.aruna.kliknelayanwakatobi.tools.Utils

/**
 * Created by marzellaalfamega on 12/5/17.
 */
class FormButton(context: Context, title: String, listener: OnClickListener?) : FormBase(context, title) {
    override fun getValue(): String {
        return ""
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.form_button, null, false)

        val btnForm = view.findViewById<Button>(R.id.btnForm)
        btnForm.text = title
        Utils.Log("FormButton", "form button init")
        if (listener != null) {
            Utils.Log("FormButton", "form button listener not null")
            btnForm.setOnClickListener(listener)
        }else{
            Utils.Log("FormButton", "form button listener null")
        }

        addView(view)
    }

    override fun isSkipCheck(): Boolean {
        return true
    }
}