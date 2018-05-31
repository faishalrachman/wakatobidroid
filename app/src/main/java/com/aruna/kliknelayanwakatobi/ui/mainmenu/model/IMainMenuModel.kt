package com.aruna.kliknelayanwakatobi.ui.mainmenu.model

import com.aruna.kliknelayanwakatobi.ui.mainmenu.presenter.IMainMenuPresenter

/**
 * Created by marzellaalfamega on 12/8/17.
 */
interface IMainMenuModel {
    fun setupModelListener(presenter: IMainMenuPresenter)
    fun loadUser()
}