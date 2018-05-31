package com.aruna.kliknelayanwakatobi.ui

import android.support.v7.widget.Toolbar
import android.view.MenuItem


/**
 * Created by marzellaalfamega on 12/5/17.
 */
open class BaseActivityWithUp : BaseActivity() {

    protected fun setupToolbar(toolbar: Toolbar) {

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.getItemId()) {
//            android.R.id.home -> {
//                onBackPressed()
//                return true
//            }
//            else -> {
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

}