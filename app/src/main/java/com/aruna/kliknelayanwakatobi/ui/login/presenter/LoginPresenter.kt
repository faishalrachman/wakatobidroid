package com.aruna.kliknelayanwakatobi.ui.login.presenter

import com.aruna.kliknelayanwakatobi.ui.login.model.ILoginModel
import com.aruna.kliknelayanwakatobi.ui.login.view.ILoginView

/**
 * Created by marzellaalfamega on 12/3/17.
 */
class LoginPresenter(val view: ILoginView, val model: ILoginModel) : ILoginPresenter {
    override fun gotoMainMenu() {
        view.gotoMainMenu()
    }

    override fun showMessage(msg: String) {
        view.showMessage(msg)
    }

    override fun doLogin() {
        view.showLoading()
        model.doLogin(view.getUsername(),view.getPassword())
    }

    override fun setupModelListener() {
        model.setupModelListener(this)
    }
}