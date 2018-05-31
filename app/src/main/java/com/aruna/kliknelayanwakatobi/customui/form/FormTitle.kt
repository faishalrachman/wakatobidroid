package com.aruna.kliknelayanwakatobi.customui.form

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.aruna.kliknelayanwakatobi.R

/**
 * Created by marzellaalfamega on 12/5/17.
 */
class FormTitle(context: Context, title: String) : FormBase(context, title) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.form_title, null, false)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitleFormTitle)
        tvTitle.text = title

        addView(view)
    }

    override fun getValue(): String {
        return ""
    }

    override fun isSkipCheck(): Boolean {
        return true
    }
}