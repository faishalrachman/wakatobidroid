package com.aruna.kliknelayanwakatobi.ui.login.model

import com.aruna.kliknelayanwakatobi.ui.login.presenter.ILoginPresenter

/**
 * Created by marzellaalfamega on 12/3/17.
 */
interface ILoginModel {
    fun setupModelListener(loginPresenter: ILoginPresenter)
    fun doLogin(username: String, password: String)
}