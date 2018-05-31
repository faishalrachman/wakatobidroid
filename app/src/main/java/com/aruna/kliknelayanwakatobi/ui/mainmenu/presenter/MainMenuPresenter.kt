package com.aruna.kliknelayanwakatobi.ui.mainmenu.presenter

import com.aruna.kliknelayanwakatobi.pojo.ModelUserData
import com.aruna.kliknelayanwakatobi.ui.mainmenu.model.IMainMenuModel
import com.aruna.kliknelayanwakatobi.ui.mainmenu.view.IMainMenuActivity

/**
 * Created by marzellaalfamega on 12/8/17.
 */
class MainMenuPresenter(val view: IMainMenuActivity, val model: IMainMenuModel) : IMainMenuPresenter {
    override fun initDataUser(modelUserData: ModelUserData) {
        view.initDataUser(modelUserData)
    }

    override fun loadUser() {
        model.loadUser()
    }

    override fun setupModelListener() {
        model.setupModelListener(this)
    }
}