package com.aruna.kliknelayanwakatobi.ui.login.presenter

/**
 * Created by marzellaalfamega on 12/3/17.
 */
interface ILoginPresenter {
    fun setupModelListener()
    fun doLogin()
    fun gotoMainMenu()
    fun showMessage(msg: String)
}