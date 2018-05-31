package com.qasico.aruna.extension

import android.text.TextUtils
import android.widget.EditText

/**
 * Created by marzellaalfamega on 10/16/17.
 */
fun EditText.isEmpty(): Boolean {
    val kosong = TextUtils.isEmpty(this.text)
    if (kosong) {
        this.error = "Harus di isi"
        this.requestFocus()
    }
    return kosong
}

fun EditText.getString(): String {
    return this.text.toString()
}