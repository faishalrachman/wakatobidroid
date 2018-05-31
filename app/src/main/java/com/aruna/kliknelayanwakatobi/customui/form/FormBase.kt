package com.aruna.kliknelayanwakatobi.customui.form

import android.content.Context
import android.widget.FrameLayout

/**
 * Created by marzellaalfamega on 12/13/17.
 */
abstract class FormBase(context: Context, title: String) : FrameLayout(context) {

    abstract fun getValue(): Any

    open fun isSkipCheck(): Boolean {
        return false
    }
}