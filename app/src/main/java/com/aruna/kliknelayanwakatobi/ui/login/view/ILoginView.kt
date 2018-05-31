package com.aruna.kliknelayanwakatobi.ui.login.view

/**
 * Created by marzellaalfamega on 12/3/17.
 */
interface ILoginView {
    fun getUsername(): String
    fun getPassword(): String
    fun gotoMainMenu()
    fun showMessage(msg: String)
    fun showLoading()
}